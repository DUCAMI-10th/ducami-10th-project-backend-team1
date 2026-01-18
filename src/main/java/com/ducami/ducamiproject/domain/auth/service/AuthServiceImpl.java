package com.ducami.ducamiproject.domain.auth.service;

import com.ducami.ducamiproject.domain.auth.dto.request.SignupRequest;
import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;
import com.ducami.ducamiproject.domain.auth.dto.response.RefreshResponse;
import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.global.security.jwt.JwtExtract;
import com.ducami.ducamiproject.global.security.jwt.JwtProvider;
import com.ducami.ducamiproject.global.security.jwt.enums.TokenType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtExtract jwtExtract;
    private final UserService userService;

    @Override
    public void signup(String name, String email, String password) {
        userService.checkEmail(email);

        UserEntity user = UserEntity.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role(UserRole.ROLE_USER)
                .build();
        userService.save(user);
    }

    @Override
    public void signup(SignupRequest request) {
        userService.checkUsername(request.username());
        userService.checkEmail(request.email());

        UserEntity user = UserEntity.builder()
            .name(request.name())
            .password(passwordEncoder.encode(request.password()))
            .email(request.email())
            .username(request.username())
            .generation(request.generation())
            .role(UserRole.ROLE_USER)
            .build();
        user.setStudentId(request.studentId());

        userService.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(String username, String password) {
        UserEntity user = userService.findByUsername(username)
            .orElseThrow(() -> new AuthException(AuthStatusCode.INVALID_CREDENTIALS));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(AuthStatusCode.INVALID_CREDENTIALS);
        }

        String access = jwtProvider.generateAccessToken(username, user.getRole());
        String refresh = jwtProvider.generateRefreshToken(username, user.getRole());
        return new LoginResponse(user.getId(), access, refresh);
    }

    @Override
    public RefreshResponse refresh(String refreshToken) {
        Claims claims = jwtProvider.getClaims(refreshToken).getPayload();
        jwtExtract.checkTokenType(claims, TokenType.REFRESH);

        UserEntity user = userService.findByUsername(claims.getSubject())
            .orElseThrow(() -> new AuthException(AuthStatusCode.INVALID_CREDENTIALS));
        return new RefreshResponse(
            jwtProvider.generateAccessToken(claims.getSubject(), user.getRole())
        );
    }


}
