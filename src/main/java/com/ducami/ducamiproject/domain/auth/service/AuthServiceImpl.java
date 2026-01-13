package com.ducami.ducamiproject.domain.auth.service;

import com.ducami.ducamiproject.domain.auth.dto.response.LoginResponse;
import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.global.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Override
    @Transactional
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
    public LoginResponse login(String email, String password) {
        UserEntity user = userService.findByEmail(email)
            .orElseThrow(() -> new AuthException(AuthStatusCode.INVALID_CREDENTIALS));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(AuthStatusCode.INVALID_CREDENTIALS);
        }

        String access = jwtProvider.generateAccessToken(email, user.getRole());
        String refresh = jwtProvider.generateRefreshToken(email, user.getRole());
        return new LoginResponse(user.getId(), access, refresh);
    }


}
