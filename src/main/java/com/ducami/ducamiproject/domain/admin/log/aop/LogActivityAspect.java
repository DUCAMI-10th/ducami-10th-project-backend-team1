package com.ducami.ducamiproject.domain.admin.log.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogActivityAspect {

    @Around("execution(* com.ducami.ducamiproject.domain.*.service.*.*(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethod(joinPoint);

        return joinPoint.proceed();
    }

    // 구현된 서비스의 인터페이스의 Method를 찾습니다. 만약 interface에 구현되지 않았다면 바로 구현한 메서드를 반환합니다.
    private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method implMethod = signature.getMethod();
        Object target = joinPoint.getTarget();

        for (Class<?> iface : target.getClass().getInterfaces()) {
            try {
                return iface.getMethod(
                        implMethod.getName(),
                        implMethod.getParameterTypes()
                );
            }
            catch (NoSuchMethodException ignored) {}
        }

        return implMethod;
    }

}
