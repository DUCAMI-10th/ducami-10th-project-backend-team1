package com.ducami.ducamiproject.domain.admin.log.aspect;

import com.ducami.ducamiproject.domain.admin.log.annotation.LogActivity;
import com.ducami.ducamiproject.domain.admin.log.annotation.target.LogTargetPK;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ActivityLogAspect {
    private final List<LogActivityResolver> resolvers;
    private final AdminLogService adminLogService;

    @Around("@annotation(logActivity)")
    public Object log(ProceedingJoinPoint joinPoint, LogActivity logActivity) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<LogActivityResolver> opt = resolvers.stream()
                .filter(r -> r.supports(logActivity.target()))
                .findFirst();
        if (opt.isEmpty()) { return joinPoint.proceed(); }
        LogActivityResolver resolver = opt.get();

        Map<String, Object> params = extractParams(joinPoint);
        Long targetId = getTargetId(joinPoint);
        if (targetId != null) {
            Map<String, Object> before = resolver.before(targetId);
            params.putAll(before);
        }
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

    // targetId를 하나만 잡도록 ( 나중에 수정 )
    private Long getTargetId(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof LogTargetPK) {
                    if (args[i] instanceof Long) {
                        return (Long) args[i];
                    }
                }
            }
        }
        return null;
    }

    private Map<String, Object> extractParams(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations(); //[파라미터순서][어노테이션들]
        Object[] args = joinPoint.getArgs();
        boolean isPk;
        Map<String, Object> logMap = new HashMap<>();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            isPk = false;
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof LogTargetPK) {
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