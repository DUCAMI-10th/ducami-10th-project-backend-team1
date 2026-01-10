package com.ducami.ducamiproject.infra.log.message.enricher;


import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;
import com.ducami.ducamiproject.infra.log.enricher.DefaultContextEnricher;
import com.ducami.ducamiproject.infra.log.message.resolver.LogMessageResolver;
import com.ducami.ducamiproject.infra.log.message.resolver.SpELMessageResolver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

@Component
public class LogMessageEnricher extends DefaultContextEnricher {
    private final LogMessageResolver resolver = new SpELMessageResolver();

    @Override
    public void enrich(LogActivityContext context) {
        context.setMessage(resolver.render(context));
    }
}
