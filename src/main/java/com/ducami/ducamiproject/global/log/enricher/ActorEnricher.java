package com.ducami.ducamiproject.global.log.enricher;

import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import com.ducami.ducamiproject.global.security.resolver.ActorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ActorEnricher extends DefaultContextEnricher {
    private final ActorProvider<?> provider;

    @Override
    public void enrich(LogActivityContext context) {
        context.setActor(provider.getActor());
    }
}
