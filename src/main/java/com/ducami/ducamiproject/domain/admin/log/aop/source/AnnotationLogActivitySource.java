package com.ducami.ducamiproject.domain.admin.log.aop.source;

import com.ducami.ducamiproject.domain.admin.log.annotation.LogActivity;
import org.springframework.core.annotation.AnnotatedElementUtils;
import java.lang.reflect.Method;

public class AnnotationLogActivitySource implements LogActivitySource {
    @Override
    public LogActivity getLogActivity(Method method) {
        LogActivity ann = AnnotatedElementUtils.findMergedAnnotation(method, LogActivity.class);
        return ann;
    }
}
