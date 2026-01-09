package com.ducami.ducamiproject.infra.log.aop.source;

import com.ducami.ducamiproject.infra.log.annotation.LogActivity;

import java.lang.reflect.Method;

public interface LogActivitySource {
    static LogActivity getLogActivity(Method method) {
        return null;
    }

}
