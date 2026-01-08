package com.ducami.ducamiproject.global.security.annotation;

import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.global.security.MockSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockUserEntity {

    long id() default 1L;
    String name() default "name";
    String email() default "email@a.com";
    UserRole role() default UserRole.ROLE_USER;
}
