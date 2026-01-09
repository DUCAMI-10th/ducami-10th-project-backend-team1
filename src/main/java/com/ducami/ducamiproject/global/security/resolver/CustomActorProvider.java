package com.ducami.ducamiproject.global.security.resolver;

import com.ducami.ducamiproject.global.entity.UserActor;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
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

        return UserActor.anonymous();
    }

}
