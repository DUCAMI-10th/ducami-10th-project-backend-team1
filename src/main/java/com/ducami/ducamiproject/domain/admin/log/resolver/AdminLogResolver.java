package com.ducami.ducamiproject.domain.admin.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;

import java.util.Map;

public interface AdminLogResolver {

    boolean supports(TargetType type);

    /**
     * 대상 객체의 변경 전 상태를 조회하여 반환합니다.
     * @param targetId 대상 객체의 ID
     * @return 변경 전 상태를 담은 Map
     */
    Map<String, Object> resolveBefore(Object targetId);


    /**
     * 변경 전/후 상태를 바탕으로 최종 로그 메시지를 생성합니다.
     * @param beforeData resolveBefore에서 반환된 변경 전 상태
     * @param targetId 대상 객체의 ID
     * @param action 수행된 AdminAction
     * @return 최종 로그 메시지 문자열
     */
    String getDetails(Map<String, Object> beforeData, Object targetId, AdminAction action);
}
