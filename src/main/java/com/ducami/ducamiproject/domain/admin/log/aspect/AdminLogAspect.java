package com.ducami.ducamiproject.domain.admin.log.aspect;

import com.ducami.ducamiproject.domain.admin.log.annotation.AdminLog;
import com.ducami.ducamiproject.domain.admin.log.annotation.LogTargetId;
import com.ducami.ducamiproject.domain.admin.log.domain.AdminLogEntity;
import com.ducami.ducamiproject.domain.admin.log.resolver.AdminLogResolver;
import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.exception.exception.ApplicationException;
import com.ducami.ducamiproject.global.exception.status_code.CommonStatusCode;
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
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminLogAspect {

    private final AdminLogService adminLogService;
    private final List<AdminLogResolver> resolvers;

    @Around("@annotation(adminLog)")
    @Transactional
    public Object log(ProceedingJoinPoint joinPoint, AdminLog adminLog) throws Throwable {
        // 1. 현재 사용자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return joinPoint.proceed(); // 관리자 정보 없으면 그냥 진행
        }
        UserEntity actor = userDetails.getUser();

        // 2. 적절한 Resolver 찾기
        AdminLogResolver resolver = resolvers.stream()
                .filter(r -> r.supports(adminLog.target()))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CommonStatusCode.INVALID_ARGUMENT, "지원하는 로그 리졸버를 찾을 수 없습니다."));

        // 3. @LogTargetId 어노테이션에서 대상 ID 추출
        Object targetId = getTargetId(joinPoint);
        if (targetId == null) {
            log.warn("@AdminLog is used, but @LogTargetId is not found in method arguments: {}", joinPoint.getSignature().getName());
            return joinPoint.proceed(); // 대상 ID 없으면 그냥 진행
        }

        // 4. 메소드 실행 전 상태 저장 (1차 캐시 혹은 DB에서 조회)
        Map<String, Object> beforeData = resolver.resolveBefore(targetId);

        // 5. 원래 메소드 실행
        Object result = joinPoint.proceed();

        // 6. 최종 로그 메시지 생성 (1차 캐시에서 조회)
        String details = resolver.getDetails(beforeData, targetId, adminLog.action());

        // 7. 로그 엔티티 생성 및 저장
        AdminLogEntity logEntity = AdminLogEntity.builder()
                .actor(actor)
                .actionType(adminLog.action().name().toLowerCase())
                .details(details)
                .build();
        
        adminLogService.saveLog(logEntity);

        return result;
    }

    private Object getTargetId(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            for (Annotation annotation : method.getParameterAnnotations()[i]) {
                if (annotation instanceof LogTargetId) {
                    Object arg = args[i];
                    // DTO 나 다른 객체인 경우 ID를 추출하려는 시도 (getId, getUserId 등)
                    // 지금은 Long, Integer, String 타입만 직접적인 ID로 간주
                    if (arg instanceof Long || arg instanceof Integer || arg instanceof String) {
                        return arg;
                    }
                    // TODO: 더 복잡한 객체에서 ID를 추출하는 로직 추가 가능 (예: SpEL 사용)
                    log.warn("Complex object type used with @LogTargetId, but ID extraction logic is not implemented. Arg: {}", arg);
                    return null;
                }
            }
        }
        return null;
    }
}
