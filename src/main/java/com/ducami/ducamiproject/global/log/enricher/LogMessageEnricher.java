package com.ducami.ducamiproject.global.log.enricher;


import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import com.ducami.ducamiproject.global.log.resolver.LogMessageResolver;
import com.ducami.ducamiproject.global.log.resolver.SpELMessageResolver;

public class LogMessageEnricher extends DefaultContextEnricher {
    private final LogMessageResolver resolver = new SpELMessageResolver();

    @Override
    public void enrich(LogActivityContext context) {
        context.setMessage(resolver.render(context));
    }
}
