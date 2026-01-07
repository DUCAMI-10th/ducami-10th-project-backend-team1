package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface ActorProvider<A> {
    A getActor();
}
