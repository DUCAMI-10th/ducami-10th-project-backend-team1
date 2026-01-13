package com.ducami.ducamiproject.global.security;

import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (UserEntity) ((CustomUserDetails) principal).getUser();
        }
        return null;
    }
}
