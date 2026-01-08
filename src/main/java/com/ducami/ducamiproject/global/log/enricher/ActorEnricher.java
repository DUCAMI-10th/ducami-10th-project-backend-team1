package com.ducami.ducamiproject.global.log.enricher;

import com.ducami.ducamiproject.global.entity.UserActor;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import com.ducami.ducamiproject.global.security.resolver.ActorProvider;
import com.ducami.ducamiproject.global.security.resolver.CustomActorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class ActorEnricher extends DefaultContextEnricher {
    private final ActorProvider<?> provider;

    @Override
    public void enrich(LogActivityContext context) {
        context.setActor(provider.getActor());
    }
}
