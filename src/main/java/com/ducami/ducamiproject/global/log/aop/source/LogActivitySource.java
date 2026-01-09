package com.ducami.ducamiproject.global.log.aop.source;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;

import java.lang.reflect.Method;

public interface LogActivitySource {
    static LogActivity getLogActivity(Method method) {
        return null;
    }

}
