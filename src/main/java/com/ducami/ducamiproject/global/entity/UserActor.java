package com.ducami.ducamiproject.global.entity;

import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.infra.log.entity.Actor;

public class UserActor extends Actor {

    public UserActor(Long id, String username) {
        super(id, username);
    }

    public static UserActor of(UserEntity entity) {
        return new UserActor(entity.getId(), entity.getName());
    }

    public static UserActor anonymous() {
        return new UserActor(null, "anonymousUser");
    }
}
