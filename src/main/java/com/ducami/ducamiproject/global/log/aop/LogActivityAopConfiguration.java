package com.ducami.ducamiproject.global.log.aop;


import com.ducami.ducamiproject.global.log.enricher.ContextEnricher;
import com.ducami.ducamiproject.global.log.resolver.LogActivityResolver;
import com.ducami.ducamiproject.global.log.sink.LogActivitySink;
import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class LogActivityAopConfiguration {

    private final List<LogActivityResolver> resolvers;
    private final List<ContextEnricher> enrichers;
    private final LogActivitySink sink;
    private final LogActivityAdvice logActivityAdvice;

    @Bean
    public DefaultPointcutAdvisor logActivityAdvisor() { // 임시수정
        Pointcut pointcut = new LogPointcut();

        return new DefaultPointcutAdvisor(pointcut, logActivityAdvice);
    }

}
