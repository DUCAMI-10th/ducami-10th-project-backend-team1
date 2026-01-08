package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.log.entity.Actor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class LogActivityContext {
    private TargetType target;
    private AdminAction action;
    private String template;

    private Map<String, Object> targetEntities;
    private Map<String, Object> params;

    private Object proceed;
    private Actor actor;

}
