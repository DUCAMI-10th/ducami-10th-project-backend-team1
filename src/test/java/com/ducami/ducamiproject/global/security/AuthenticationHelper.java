package com.ducami.ducamiproject.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper<U> {

    private Class<U> clazz;

    public AuthenticationHelper(Class<U> clazz) {
        this.clazz = clazz;
    }

    public U getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (clazz.isInstance(principal)) {
            return clazz.cast(principal);
        }
        return null;
    }
}
