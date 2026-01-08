package com.ducami.ducamiproject.global.log.aop;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.stereotype.Component;

@Component
public class LogActivityAdvisor extends DefaultPointcutAdvisor {

    public LogActivityAdvisor(LogPointcut pointcut, LogActivityAdvice advice) {
        super(pointcut, advice);
    }
}
