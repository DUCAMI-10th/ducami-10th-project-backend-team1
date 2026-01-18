package com.ducami.ducamiproject.infra.log.aop;

import com.ducami.ducamiproject.infra.log.annotation.LogActivity;
import com.ducami.ducamiproject.infra.log.enricher.ContextEnricher;
import com.ducami.ducamiproject.infra.log.resolver.LogActivityResolver;
import com.ducami.ducamiproject.infra.log.sink.LogActivitySink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.ducami.ducamiproject.infra.log.aop.source.AnnotationLogActivitySource.getLogActivity;


@RequiredArgsConstructor
@Slf4j
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LogActivityAdvice implements MethodInterceptor {

    protected final ObjectProvider<List<LogActivityResolver>> resolversProvider;
    protected final ObjectProvider<LogActivitySink> sinkProvider;
    protected final ObjectProvider<List<ContextEnricher>> enrichersProvider;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LogActivitySink sink = sinkProvider.getIfAvailable();
        List<LogActivityResolver> resolvers = resolversProvider.getIfAvailable();

        LogActivity logActivity = getLogActivity(invocation.getMethod());

        Optional<LogActivityResolver> resolverOpt = resolvers.stream()
                .filter(r -> r.supports(logActivity.target()))
                .findFirst();
        if (resolverOpt.isEmpty()) {
            log.warn("No log activity resolver found for {}", logActivity.target());
            return invocation.proceed();
        }

        LogActivityContext context = resolverOpt.get().resolve(invocation, logActivity);
        enrich(context);
        sink.emit(context);

        return context.getProceed();
    }

    public void enrich(LogActivityContext context) {
        List<ContextEnricher> enrichers = enrichersProvider.getIfAvailable();

        for (ContextEnricher enricher : enrichers) {
            enricher.enrich(context);
        }
    }
}