package com.ducami.ducamiproject.global.enricher;

import com.ducami.ducamiproject.infra.log.aop.LogActivityContext;
import com.ducami.ducamiproject.infra.log.resolver.ActorProvider;
import com.ducami.ducamiproject.infra.log.enricher.DefaultContextEnricher;
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
