package com.ducami.ducamiproject.infra.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.infra.log.annotation.LogActivity;
import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Map;

public interface LogActivityResolver {
    boolean supports(TargetType target);

    Map<String, Object> before(Map<String, Object> targetIds);

    Map<String, Object> toSnapshot(Object entity);

    LogActivityContext resolve(MethodInvocation invocation, LogActivity logActivity) throws Throwable;
}
