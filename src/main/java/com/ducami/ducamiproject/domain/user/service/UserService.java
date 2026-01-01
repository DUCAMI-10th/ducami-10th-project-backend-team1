package com.ducami.ducamiproject.domain.user.service;

import com.ducami.ducamiproject.domain.admin.log.annotation.LogActivity;
import com.ducami.ducamiproject.domain.admin.log.annotation.LogTarget;
import com.ducami.ducamiproject.domain.admin.log.annotation.target.LogTargetEnum;
import com.ducami.ducamiproject.domain.admin.log.annotation.target.LogTargetPK;
import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.dto.request.UpdateUserRoleRequest;
import com.ducami.ducamiproject.domain.user.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.exception.UserException;
import com.ducami.ducamiproject.domain.user.exception.UserStatusCode;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AuthException(AuthStatusCode.ALREADY_EXISTS);
        }
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserInfoResponse getUserInfo(String email) {
        UserEntity user = findByEmail(email)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
        return UserInfoResponse.from(user);
    }

    @Transactional
    @LogActivity(
            target = TargetType.USER,
            action = AdminAction.CHANGE_ROLE,
            template = "{name} 부원의 권한을 [{beforeRole}]에서 [{userRole}]로 변경했습니다."
    )
    public void updateUserRole(@LogTargetPK Long id, UserRole userRole) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
        user.setRole(userRole);
        userRepository.save(user);
    }
}
