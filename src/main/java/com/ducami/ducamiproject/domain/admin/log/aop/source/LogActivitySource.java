package com.ducami.ducamiproject.domain.admin.log.aop.source;

import com.ducami.ducamiproject.domain.admin.log.annotation.LogActivity;

import java.lang.reflect.Method;

public interface LogActivitySource {
    LogActivity getLogActivity(Method method);
}
