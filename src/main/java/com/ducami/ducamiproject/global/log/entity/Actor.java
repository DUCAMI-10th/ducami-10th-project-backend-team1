package com.ducami.ducamiproject.global.log.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Actor {
    private Long userId;
    private String username;

    public static Actor anonymous() {
        return new Actor(null, "anonymousUser");
    }
}
