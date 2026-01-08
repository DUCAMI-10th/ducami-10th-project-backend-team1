package com.ducami.ducamiproject.global.log.enricher;

import com.ducami.ducamiproject.global.log.aop.LogActivityContext;

public interface ContextEnricher {
    void enrich(LogActivityContext context);
}
