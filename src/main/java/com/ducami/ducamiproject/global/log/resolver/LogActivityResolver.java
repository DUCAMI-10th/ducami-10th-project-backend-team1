package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Map;

public interface LogActivityResolver {
    boolean supports(TargetType target);

    Map<String, Object> before(Map<String, Object> targetIds);

    Map<String, Object> toSnapshot(Object entity);

    LogActivityContext resolve(MethodInvocation invocation, LogActivity logActivity) throws Throwable;
}
