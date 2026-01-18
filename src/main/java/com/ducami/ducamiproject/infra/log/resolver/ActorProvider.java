package com.ducami.ducamiproject.infra.log.resolver;

import com.ducami.ducamiproject.infra.log.entity.Actor;


public interface ActorProvider<A extends Actor> {
    A getActor();
}
