package com.ducami.ducamiproject.domain.user.service;

import com.ducami.ducamiproject.infra.log.annotation.LogActivity;
import com.ducami.ducamiproject.infra.log.annotation.target.LogTargetEntity;
import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.user.enums.UserRole;

import java.util.Optional;

public interface UserService {

    void checkEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity userEntity);

    UserInfoResponse getUserInfo(String email);

    @LogActivity(
        target = TargetType.USER,
        action = AdminAction.CHANGE_ROLE,
        template = "{user.name} 부원의 권한을 [{user.beforeRole}]에서 [{userRole}]로 변경했습니다."
    )
    void updateUserRole(@LogTargetEntity("user") Long id, UserRole userRole);

}
