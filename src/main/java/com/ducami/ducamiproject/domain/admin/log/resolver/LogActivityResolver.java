package com.ducami.ducamiproject.domain.admin.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;

import java.util.Map;

public interface LogActivityResolver {
    boolean supports(TargetType target);

    Map<String, Object> before(Long id);

    String resolve(Map<String, Object> params, String message);
}
