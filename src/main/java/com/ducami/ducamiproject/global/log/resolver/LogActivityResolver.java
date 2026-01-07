package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;

import java.util.Map;

public interface LogActivityResolver {
    boolean supports(TargetType target);

    Map<String, Object> before(Map<String, Object> targetIds);

    String resolve(Map<String, Object> params, String message);

    Map<String, Object> toSnapshot(Object entity);
}
