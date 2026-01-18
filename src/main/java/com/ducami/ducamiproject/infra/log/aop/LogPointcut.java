package com.ducami.ducamiproject.infra.log.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.ducami.ducamiproject.infra.log.aop.source.AnnotationLogActivitySource.getLogActivity;

@Component
@RequiredArgsConstructor
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LogPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        return getLogActivity(specificMethod) != null;
    }
}
