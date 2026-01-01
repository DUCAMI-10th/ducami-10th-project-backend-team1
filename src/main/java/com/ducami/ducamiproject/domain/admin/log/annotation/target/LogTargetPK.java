package com.ducami.ducamiproject.domain.admin.log.annotation.target;

import com.ducami.ducamiproject.domain.admin.log.annotation.LogTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그 기록을 위해 DB에서 데이터를 조회할 때 사용할 PK(ID) 파라미터를 지정합니다.
 * * <p>이 어노테이션이 붙은 파라미터 값은 AOP에서 추출되어
 * {@code LogActivityResolver.before(Long id)}의 인자로 전달됩니다.</p>
 * * <p>리졸버는 이 ID를 통해 <b>변경 전의 Entity</b>를 조회하고,
 * 로그 템플릿에서 사용할 '이전 데이터'들을 추출하여 Map에 담습니다.</p>
 * * @see com.ducami.ducamiproject.domain.admin.log.resolver.LogActivityResolver
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@LogTarget(Long.class)
public @interface LogTargetPK {
    String value() default "";
}
