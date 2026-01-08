package com.ducami.ducamiproject.domain.admin.log.dto;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.log.entity.Actor;

public class UserActor extends Actor {

    public UserActor(Long id, String username) {
        super(id, username);
    }

    public static UserActor of(UserEntity entity) {
        return new UserActor(entity.getId(), entity.getName());
    }
}
