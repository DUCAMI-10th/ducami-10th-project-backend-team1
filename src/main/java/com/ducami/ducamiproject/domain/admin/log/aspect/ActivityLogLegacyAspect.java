package com.ducami.ducamiproject.domain.admin.log.aspect;

import com.ducami.ducamiproject.domain.admin.log.annotation.LogActivity;
import com.ducami.ducamiproject.domain.admin.log.annotation.target.LogTargetEntity;
import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.resolver.LogActivityResolver;
import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ActivityLogLegacyAspect {
    private final List<LogActivityResolver> resolvers;
    private final AdminLogService adminLogService;

    @Around("execution(* com.ducami.ducamiproject.domain.*.service.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint, LogActivity logActivity) throws Throwable {
        System.out.println("반갑토");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Optional<LogActivityResolver> opt = resolvers.stream()
                .filter(r -> r.supports(logActivity.target()))
                .findFirst();
        if (opt.isEmpty()) {
            log.warn("No log activity resolver found for {}", logActivity.target());
             return joinPoint.proceed();
        }
        LogActivityResolver resolver = opt.get();

        Map<String, Object> params = extractParams(joinPoint);
        Map<String, Object> targetIds = getTargetIds(joinPoint);
        params.putAll(resolver.before(targetIds));
        Object result = joinPoint.proceed();
        params.put("_res", result);

        String message = resolver.resolve(params, logActivity.template());
        AdminLogEntity log = AdminLogEntity.builder()
                .actor(userDetails.getUser())
                .actionType(logActivity.action())
                .details(message)
                .build();
        adminLogService.saveLog(log);

        return result;
    }

    private Method resolveMethod(ProceedingJoinPoint joinPoint) {
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

    private Map<String, Object> getTargetIds(ProceedingJoinPoint joinPoint) {
        Method method = resolveMethod(joinPoint);

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> targetIds = new HashMap<>();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof LogTargetEntity logTargetEntity) {
                    targetIds.put(logTargetEntity.value(), args[i]);
                    break;
                }
            }
        }
        return targetIds;

    }

    private Map<String, Object> extractParams(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Method method = resolveMethod(joinPoint);
        Annotation[][] parameterAnnotations = method.getParameterAnnotations(); //[파라미터순서][어노테이션들]

        boolean isPk;
        Map<String, Object> logMap = new HashMap<>();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            isPk = false;
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof LogTargetEntity) {
                    isPk = true;
                    break;
                }
            }
            if (!isPk) {
                logMap.put(parameterNames[i], args[i]);
            }
        }
        return logMap;
    }
}

// before에서 필요한 정보를 뽑아옴 (음 이때 Entity에서 뽑아두면 좋은걸 Entity에 지정해놔도 좋음 그래서 message안에 넣는 상수로 지정해두는거롤?)
// 이때 필요한 메서드에 있는 LogTarget과 필요한 정보를 바탕으로 map형태로
// Target으로 가져올 때 Map형태로 해서 반환
// 실행 후 after
// 여기서 template에 필요한걸로 조립함