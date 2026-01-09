package com.ducami.ducamiproject.infra.log.sink;

import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;

public interface LogActivitySink {
    void emit(LogActivityContext context);
}
