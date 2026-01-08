package com.ducami.ducamiproject.global.log.sink;

import com.ducami.ducamiproject.global.log.aop.LogActivityContext;

public interface LogActivitySink {
    void emit(LogActivityContext context);
}
