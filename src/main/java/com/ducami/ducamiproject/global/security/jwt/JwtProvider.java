package com.ducami.ducamiproject.global.security.jwt;


import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.enums.UserRole;
import com.ducami.ducamiproject.global.security.jwt.config.JwtProperties;
import com.ducami.ducamiproject.global.security.jwt.enums.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final SecretKey key;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public Jws<Claims> getClaims(String token) {
        try {
            return Jwts.parser(). // parser 수정
                    verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        }
        catch (ExpiredJwtException e) {
            throw new AuthException(AuthStatusCode.EXPIRED_TOKEN);
        }
        catch (IllegalArgumentException e) {
            throw new AuthException(AuthStatusCode.INVALID_JWT);
        }
        catch (MalformedJwtException e) {
            throw new AuthException(AuthStatusCode.INVALID_JWT, "JWT 토큰 형식이 올바르지 않습니다.");
        }
    }

    public String generateToken(TokenType tokenType, String googleId, UserRole role, long expiration) {
        Instant now = Instant.now();
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(googleId)
                .claim("token_type", tokenType.name())
                .claim("authority", role.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration, ChronoUnit.MILLIS)))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String generateAccessToken(String googleId, UserRole role) {
        return generateToken(TokenType.ACCESS, googleId, role, jwtProperties.getAccessExpiration());
    }

    public String generateRefreshToken(String googleId, UserRole role) {
        return generateToken(TokenType.REFRESH, googleId, role, jwtProperties.getRefreshExpiration());
    }

}
