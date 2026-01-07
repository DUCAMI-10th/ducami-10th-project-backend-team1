package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.annotation.target.LogTargetEntity;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import com.ducami.ducamiproject.global.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.*;

public abstract class DefaultActivityResolver implements LogActivityResolver {
    private static final ParameterNameDiscoverer nameDiscoverer =
            new DefaultParameterNameDiscoverer();

    @Override
    @Deprecated
    public String resolve(Map<String, Object> params, String message) {
        return null;
    }

    @Override
    public LogActivityContext resolve(MethodInvocation invocation, LogActivity logActivity) throws Throwable {
        Class<?> targetClass = getTargetClass(invocation);
        Method method = getMethod(invocation, targetClass);
        Object[] args = invocation.getArguments();

        Map<String, Object> params = extractParams(method, invocation.getArguments());
        Map<String, Object> before = before(getTargetId(targetClass, method, args));
        params.putAll(before);

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

    /**
     * 대상(Target)의 식별자(ID)를 추출합니다.
     *
     * <p>조회 우선순위는 다음과 같습니다:</p>
     * <ol>
     *   <li>현재 클래스가 구현한 <b>모든 인터페이스(상위 인터페이스 포함)</b>에 선언된 메서드</li>
     *   <li>위 인터페이스들에서 식별자를 찾지 못한 경우, 현재 클래스의 실제 구현 메서드</li>
     * </ol>
     *
     * <p>주의 사항:</p>
     * <ul>
     *   <li>구현 클래스의 <b>부모 클래스(super class)</b>에 선언된 메서드 및 어노테이션은 탐색하지 않습니다.</li>
     *   <li>첫 번째로 식별자를 발견한 메서드의 결과만 사용합니다.</li>
     * </ul>
     *
     * @param clazz  실제 타깃 객체의 클래스 (프록시가 아닌 타깃 기준)
     * @param method 실행 중인 실제 메서드
     * @param args   메서드 실행 시 전달된 인자
     * @return 추출된 대상 식별자 맵 (비어 있을 수 있음)
     */
    protected Map<String, Object> getTargetId(Class<?> clazz, Method method, Object[] args) {
        Map<String, Object> targetIds;
        Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(clazz);

        for (Class<?> iface : interfaces) {
            try {
                Method iMethod = iface.getMethod(method.getName(), method.getParameterTypes());
                targetIds = getTargetIdInMethod(iMethod, args);
                if (!targetIds.isEmpty()) {
                    return targetIds;
                }
            } catch (NoSuchMethodException ignored) {}
        }

        return getTargetIdInMethod(method, args);
    }

    protected Map<String, Object> getTargetIdInMethod(Method method, Object[] args) {
        Map<String ,Object> targetId = new HashMap<>();

        for (int i = 0; i < method.getParameterCount(); i++) {
            MethodParameter mp = new MethodParameter(method, i);
            LogTargetEntity logTargetEntity = mp.getParameterAnnotation(LogTargetEntity.class);
            if (logTargetEntity != null) {
                //TODO: 기본 'entity'로 키가 중복되는 문제
                targetId.put(logTargetEntity.value(), args[i]);
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
        String[] paramNames = nameDiscoverer.getParameterNames(method);
        Map<String, Object> params = new HashMap<>();

        if (paramNames == null) {
            return params;
        }

        for (int i = 0; i < paramNames.length; i++) {
            params.put(paramNames[i], args[i]);
        }

        return params;
    }


}
