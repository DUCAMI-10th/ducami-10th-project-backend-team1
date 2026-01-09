package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.global.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

@Configuration
@RequiredArgsConstructor
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LogActivityConfig {


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor logActivityAdvisor(LogPointcut pointcut, LogActivityAdvice advice) {
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
