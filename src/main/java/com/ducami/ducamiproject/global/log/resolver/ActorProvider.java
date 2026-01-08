package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.global.log.entity.Actor;

public interface ActorProvider<A extends Actor> {
    A getActor();
}
