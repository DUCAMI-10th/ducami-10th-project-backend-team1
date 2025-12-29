package com.ducami.ducamiproject.global.security.jwt;

import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.enums.UserRole;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import com.ducami.ducamiproject.global.security.jwt.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtExtract {

    private final JwtProvider jwtProvider;

    public String extractTokenFromRequest(HttpServletRequest request) {
        return extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    public Authentication getAuthentication(final String token) {
        final Jws<Claims> jws = jwtProvider.getClaims(token);
        final Claims claims = jws.getPayload();
        if (!checkTokenType(claims, TokenType.ACCESS)) {
            throw new AuthException(AuthStatusCode.INVALID_TOKEN_TYPE);
        }
        UserRole userRole = UserRole.valueOf(claims.get("authority", String.class));
        final UserEntity user = UserEntity.builder()
                .role(userRole)
                .build();
        final CustomUserDetails details = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    public String extractToken(final String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return token;
    }


    public boolean checkTokenType(final Claims claims, final TokenType tokenType) { //TODO: 바로 exception 날리도록 수정
        return claims.get("token_type").equals(tokenType.toString());
    }

}
