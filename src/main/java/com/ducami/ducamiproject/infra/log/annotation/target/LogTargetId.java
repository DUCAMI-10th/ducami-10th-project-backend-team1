package com.ducami.ducamiproject.infra.log.annotation.target;

import com.ducami.ducamiproject.infra.log.annotation.LogTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AdminLog가 적용된 메소드의 파라미터에 붙여, 로그 대상의 Long을 명시합니다.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@LogTarget(Long.class)
public @interface LogTargetId {
    String value() default "";
}
