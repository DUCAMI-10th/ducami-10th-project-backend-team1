package com.ducami.ducamiproject.global.security.resolver;

import com.ducami.ducamiproject.global.entity.UserActor;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;

public class TestActorProvider extends SpringSecurityActorProvider<CustomUserDetails, UserActor> {
    protected TestActorProvider() {
        super(CustomUserDetails.class);
    }

    @Override
    public UserActor getActor() {
        CustomUserDetails details = getUserDetails();
        if (details != null) {
            return UserActor.of(details.getUser());
        }

        return UserActor.anonymous();
    }
}
