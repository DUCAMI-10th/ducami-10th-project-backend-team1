package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DefaultActivityResolver implements LogActivityResolver {
    protected final ExpressionParser parser = new SpelExpressionParser();
    protected final ParserContext parserContext = new TemplateParserContext("{", "}");

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

    protected Class<?> getTargetClass(MethodInvocation invocation) {
        return AopUtils.getTargetClass(invocation);
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
