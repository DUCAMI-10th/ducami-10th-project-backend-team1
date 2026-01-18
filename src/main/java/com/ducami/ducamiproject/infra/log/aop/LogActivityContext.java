package com.ducami.ducamiproject.infra.log.aop;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.infra.log.entity.Actor;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
public class LogActivityContext {
    private TargetType target;
    private AdminAction action;
    private String template;
    private String message;

    private Map<String, Object> params;

    private Object proceed;
    private Actor actor;

}
