package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import com.ducami.ducamiproject.global.log.enricher.ContextEnricher;
import com.ducami.ducamiproject.global.log.resolver.LogActivityResolver;
import com.ducami.ducamiproject.global.log.sink.LogActivitySink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// 프록시 패턴으로 기존 코드 감싸서 메서드 실행 전에 찾고 invoke하는거 직접 해보면 좋을듯
@RequiredArgsConstructor
@Slf4j
@Component
public class LogActivityAdvice implements MethodInterceptor {

    protected final LogActivitySource source = new AnnotationLogActivitySource();
    protected final List<LogActivityResolver> resolvers;
    protected final LogActivitySink sink;
    protected final List<ContextEnricher> enrichers;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LogActivity logActivity = source.getLogActivity(invocation.getMethod());

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
        for (ContextEnricher enricher : enrichers) {
            enricher.enrich(context);
        }
    }
}