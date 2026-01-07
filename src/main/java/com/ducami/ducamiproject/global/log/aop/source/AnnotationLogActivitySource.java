package com.ducami.ducamiproject.global.log.aop.source;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class AnnotationLogActivitySource implements LogActivitySource {
    @Override
    public LogActivity getLogActivity(Method method) {
        return AnnotatedElementUtils.findMergedAnnotation(method, LogActivity.class);
    }
}
