package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.admin.log.service.AdminLogService;
import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import com.ducami.ducamiproject.global.log.resolver.DefaultActivityResolver;
import com.ducami.ducamiproject.global.log.resolver.LogActivityResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

//TODO: Advisor 단위 테스트 세팅
//TODO: Advice 로직 구현

// 프록시 패턴으로 기존 코드 감싸서 메서드 실행 전에 찾고 invoke하는거 직접 해보면 좋을듯
@RequiredArgsConstructor
@Slf4j
@Component
public class LogActivityAdvice implements MethodInterceptor {

    private final LogActivitySource source = new AnnotationLogActivitySource();
    private final List<LogActivityResolver> resolvers = List.of(
            new DefaultActivityResolver() {
                @Override
                public boolean supports(TargetType target) {
                    return target == TargetType.USER;
                }

                @Override
                public Map<String, Object> before(Map<String, Object> targetIds) {
                    return Map.of();
                }

                @Override
                public Map<String, Object> toSnapshot(Object entity) {
                    return Map.of();
                }
            }
    );

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LogActivity logActivity = source.getLogActivity(invocation.getMethod());

        Optional<LogActivityResolver> resolverOpt = resolvers.stream()
                .filter(r -> r.supports(logActivity.target()))
                .findFirst();
        if (resolverOpt.isEmpty()) {
            log.warn("No log activity resolver found for {}", logActivity.target());
            return invocation.proceed();
        }

        LogActivityContext context = resolverOpt.get().resolve(invocation, logActivity);
        return context.getProceed();
    }
}