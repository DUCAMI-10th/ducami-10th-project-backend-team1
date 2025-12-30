package com.ducami.ducamiproject.domain.admin.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import com.ducami.ducamiproject.domain.user.exception.UserException;
import com.ducami.ducamiproject.domain.user.exception.UserStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserLogResolver implements AdminLogResolver {

    private final UserRepository userRepository;

    @Override
    public boolean supports(TargetType type) {
        return type == TargetType.USER;
    }

    @Override
    public Map<String, Object> resolveBefore(Object targetId) {
        if (!(targetId instanceof Long)) {
            return new HashMap<>();
        }
        Optional<UserEntity> userOpt = userRepository.findById((Long) targetId);
        if (userOpt.isEmpty()) {
            return new HashMap<>();
        }
        UserEntity user = userOpt.get();
        Map<String, Object> beforeData = new HashMap<>();
        beforeData.put("userName", user.getName());
        beforeData.put("beforeAuthority", user.getRole().toString());
        return beforeData;
    }

    @Override
    public String getDetails(Map<String, Object> beforeData, Object targetId, AdminAction action) {
        UserEntity user = userRepository.findById((Long) targetId)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));

        String userName = user.getName();

        return switch (action) {
            case CHANGE_ROLE -> {
                String beforeAuthority = (String) beforeData.getOrDefault("beforeAuthority", "N/A");
                String afterAuthority = user.getRole().toString();
                yield String.format("%s님의 권한을 [%s]에서 [%s](으)로 변경했습니다.",
                        userName, beforeAuthority, afterAuthority);
            }
            case DELETE -> String.format("%s 사용자를 탈퇴 처리했습니다.", userName);
            default -> "지원하지 않는 작업입니다.";
        };
    }
}
