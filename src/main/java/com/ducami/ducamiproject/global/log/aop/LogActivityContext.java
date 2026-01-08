package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.log.entity.Actor;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class LogActivityContext {
    private TargetType target;
    private AdminAction action;
    private String template;
    private String message;

    private Map<String, Object> params;

    private Object proceed;
    private Actor actor;

}
