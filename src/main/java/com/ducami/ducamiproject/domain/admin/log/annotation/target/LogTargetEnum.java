package com.ducami.ducamiproject.domain.admin.log.annotation.target;

import com.ducami.ducamiproject.domain.admin.log.annotation.LogTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AdminLog가 적용된 메소드의 파라미터에 붙여, 로그 대상의 STRING임을 명시합니다.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@LogTarget(Enum.class)
public @interface LogTargetEnum {
    String value() default "";
}
