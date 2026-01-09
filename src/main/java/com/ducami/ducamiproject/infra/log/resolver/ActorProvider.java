package com.ducami.ducamiproject.infra.log.resolver;

import com.ducami.ducamiproject.infra.log.entity.Actor;


//TODO: resolver로 수정
public interface ActorProvider<A extends Actor> {
    A getActor();
}
