package com.ducami.ducamiproject.domain.admin.log.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogActivityAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("LogActivityInterceptor");
        return invocation.proceed();
    }
}