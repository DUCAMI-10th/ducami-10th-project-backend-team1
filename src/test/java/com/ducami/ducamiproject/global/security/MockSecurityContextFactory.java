package com.ducami.ducamiproject.global.security;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.security.annotation.MockUserEntity;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class MockSecurityContextFactory implements WithSecurityContextFactory<MockUserEntity> {

    @Override
    public SecurityContext createSecurityContext(MockUserEntity annotation) {
        UserEntity userEntity = UserEntity.builder()
                .name(annotation.name())
                .email(annotation.email())
                .role(annotation.role())
                .build();
        userEntity.setId(annotation.id());

        CustomUserDetails details = new CustomUserDetails(userEntity);
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);

        return context;
    }
}
