package com.ducami.ducamiproject.domain.admin.log.service;


import com.ducami.ducamiproject.domain.admin.log.annotation.FirstAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Aspect
@Service
public class AdminLogService {

    @AfterReturning("@annotation(com.ducami.ducamiproject.domain.admin.log.annotation.FirstAnnotation)")
    public void log(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        // 2. 어노테이션 정보 가져오기
        Method method = methodSignature.getMethod();
        FirstAnnotation annotation = method.getAnnotation(FirstAnnotation.class);

        System.out.println("=== Admin Log Start ===");
        System.out.println("Annotation Value: " + annotation.value());
        System.out.println("Target Object: " + joinPoint.getTarget().getClass().getSimpleName());

        // 3. 파라미터 이름과 값을 인덱스로 매칭하여 출력
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                System.out.println("Parameter -> " + parameterNames[i] + " : " + args[i]);
            }
        }
    }

}
