package com.ducami.ducamiproject.infra.log.message.resolver;

import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;

public interface LogMessageResolver {
    String render(LogActivityContext logActivityContext);
}
