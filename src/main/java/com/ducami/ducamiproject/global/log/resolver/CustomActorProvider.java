package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;

public class CustomActorProvider extends SpringSecurityActorProvider<CustomUserDetails, UserEntity> {

    public CustomActorProvider(Class<CustomUserDetails> detailsType) {
        super(detailsType);
    }

    @Override
    public UserEntity getActor() {
        CustomUserDetails details = getUserDetails();
        if (details != null) {
            return details.getUser();
        }
        return null;
    }

}
