package com.ducami.ducamiproject.global.security.resolver;

import com.ducami.ducamiproject.global.log.entity.Actor;

public interface ActorProvider<A extends Actor> {
    A getActor();
}
