package com.ducami.ducamiproject.infra.log.enricher;

import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;

public interface ContextEnricher {
    void enrich(LogActivityContext context);
}
