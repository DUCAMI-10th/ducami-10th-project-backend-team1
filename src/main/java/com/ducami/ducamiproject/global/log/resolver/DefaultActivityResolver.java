package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.annotation.target.LogTargetEntity;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import com.ducami.ducamiproject.global.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodParameter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public abstract class DefaultActivityResolver implements LogActivityResolver {
    protected final ExpressionParser parser = new SpelExpressionParser();
    protected final ParserContext parserContext = new TemplateParserContext("{", "}");
    protected final LogActivitySource source = new AnnotationLogActivitySource();

    @Override
    @Deprecated
    public String resolve(Map<String, Object> params, String message) {
        return null;
    }

    @Override
    public LogActivityContext resolve(MethodInvocation invocation, LogActivity logActivity) throws Throwable {
        Class<?> targetClass = getTargetClass(invocation);
        Method method = getMethod(invocation, targetClass);

        Map<String, Object> params = extractParams(method, invocation.getArguments());
        Object result = invocation.proceed();
        params.put("_res", result);

        return LogActivityContext.builder()
                .target(logActivity.target())
                .action(logActivity.action())
                .template(logActivity.template())
                .params(params)
                .proceed(result)
                .actor(null)
                .build();
    }

    // 기본은 인터페이스의 어노테이션을 찾습니다. 만약 인터페이스가 존재하지 않는다면 구현체에서 찾아냅니다.
    protected Map<String, Object> getTargetId(Class<?> clazz, Method method, Object[] args) {
        Class<?>[] interfaces = ClassUtils.getAllInterfaces(clazz);

        for (Class<?> iface : interfaces) {
            try {
                Method iMethod = iface.getMethod(method.getName(), method.getParameterTypes());
                return getTargetIdInMethod(iMethod, args);
            } catch (NoSuchMethodException ignored) {}
        }
        return getTargetIdInMethod(method, args);
    }

    protected Map<String, Object> getTargetIdInMethod(Method method, Object[] args) {
        Map<String ,Object> targetId = new HashMap<>();
        for (int i = 0; i < method.getParameterCount(); i++) {
            MethodParameter mp = new MethodParameter(method, i);
            if (mp.getParameterAnnotation(LogTargetEntity.class) != null) {
                targetId.put(mp.getParameterName(), args[i]);
            }
        }

        return targetId;
    }

    protected Class<?> getTargetClass(MethodInvocation invocation) {
        return AopUtils.getTargetClass(Objects.requireNonNull(invocation.getThis()));
    }

    protected Method getMethod(MethodInvocation invocation, Class<?> targetClass) {
        return AopUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
    }

    protected Map<String, Object> extractParams(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            params.put(parameters[i].getName(), args[i]);
        }

        return params;
    }


}
