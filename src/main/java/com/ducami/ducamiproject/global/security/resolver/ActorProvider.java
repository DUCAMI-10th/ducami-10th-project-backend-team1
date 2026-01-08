package com.ducami.ducamiproject.global.security.resolver;

import com.ducami.ducamiproject.global.log.entity.Actor;


//TODO: resolver로 수정
public interface ActorProvider<A extends Actor> {
    A getActor();
}
