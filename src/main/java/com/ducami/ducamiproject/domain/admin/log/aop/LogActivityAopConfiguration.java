package com.ducami.ducamiproject.domain.admin.log.aop;


import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogActivityAopConfiguration {

    @Bean
    public Advisor logActivityAdvice() {
        Pointcut pointcut = new LogPointcut();
        Advice advice = new LogActivityAdvice();

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
