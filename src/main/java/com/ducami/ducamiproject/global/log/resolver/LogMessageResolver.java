package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.global.log.aop.LogActivityContext;

public interface LogMessageResolver {
    String render(LogActivityContext logActivityContext);
}
