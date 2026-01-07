package com.ducami.ducamiproject.global.log.aop;


import com.ducami.ducamiproject.global.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class LogPointcut extends StaticMethodMatcherPointcut {

    private final LogActivitySource logActivitySource = new AnnotationLogActivitySource();

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        return logActivitySource.getLogActivity(specificMethod) != null;
    }
}
