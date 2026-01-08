package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.dto.UserActor;
import com.ducami.ducamiproject.global.log.entity.Actor;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomActorProvider extends SpringSecurityActorProvider<CustomUserDetails, UserActor> {

    public CustomActorProvider() {
        super(CustomUserDetails.class);
    }

    @Override
    public UserActor getActor() {
        CustomUserDetails details = getUserDetails();
        if (details != null) {
            return UserActor.of(details.getUser());
        }
        return null;
    }

}
