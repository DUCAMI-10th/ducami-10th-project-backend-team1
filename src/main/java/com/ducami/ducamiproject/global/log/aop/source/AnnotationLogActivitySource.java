package com.ducami.ducamiproject.global.log.aop.source;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AnnotationLogActivitySource implements LogActivitySource {
    public static LogActivity getLogActivity(Method method) {
        return AnnotatedElementUtils.findMergedAnnotation(method, LogActivity.class);
    }
}
