package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class LogPointcut extends StaticMethodMatcherPointcut {

    private final LogActivitySource logActivitySource;

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        return logActivitySource.getLogActivity(specificMethod) != null;
    }
}
