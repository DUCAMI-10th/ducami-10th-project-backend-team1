package com.ducami.ducamiproject.domain.admin.log.annotation;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 관리자 활동 로그를 기록하기 위한 어노테이션입니다.
 * * <p><b>SpEL 기반 템플릿 사용 가이드:</b></p>
 * <ul>
 * <li><b>{parameterName}</b>: 메서드 파라미터 값을 직접 참조합니다.</li>
 * <li><b>{dto.field}</b>: DTO 객체의 내부 필드에 접근합니다. (Getter 필수)</li>
 * <li><b>{beforeKey}</b>: Resolver의 before() 메서드에서 담은 이전 상태 값을 참조합니다. (각 도메인의 resolver를 참조하세요.)</li>
 * <li><b>{_res}</b>: 메서드 실행 완료 후의 <b>결과값(Return Value)</b>을 참조합니다.</li>
 * </ul>
 * * <b>예시:</b> "{beforeName}님의 권한을 {dto.role}로 변경 (ID: {_res})"
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogActivity {
    TargetType target();
    AdminAction action();
    String template() default "";
}
