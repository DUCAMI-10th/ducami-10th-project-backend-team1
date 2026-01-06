package com.ducami.ducamiproject.domain.admin.log.aop;


import com.ducami.ducamiproject.domain.admin.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.domain.admin.log.aop.source.LogActivitySource;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class LogPointcut extends StaticMethodMatcherPointcut {

    private final LogActivitySource logActivitySource = new AnnotationLogActivitySource();

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return logActivitySource.getLogActivity(method) != null;
    }
}
