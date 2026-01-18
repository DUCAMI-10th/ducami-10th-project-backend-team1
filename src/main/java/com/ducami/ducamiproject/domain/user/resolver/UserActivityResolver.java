package com.ducami.ducamiproject.domain.user.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.infra.log.resolver.DefaultActivityResolver;
import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.exception.UserException;
import com.ducami.ducamiproject.domain.user.exception.UserStatusCode;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import com.ducami.ducamiproject.global.exception.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserActivityResolver extends DefaultActivityResolver {
    private final ObjectProvider<UserRepository> repositoryProvider;

    @Override
    public boolean supports(TargetType target) {
        return TargetType.USER == target;
    }

    @Override
    public Map<String, Object> before(Map<String, Object> targetIds) {
        UserRepository userRepository = repositoryProvider.getObject();

        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : targetIds.entrySet()) {
            if (entry.getValue() instanceof Long id) {
                UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
                result.put(entry.getKey(), toSnapshot(user));
            }
        }
        return result;
            }

    @Override
    public Map<String, Object> toSnapshot(Object entity) {
        if (!(entity instanceof UserEntity user)) {
            throw new ApplicationException(UserStatusCode.NOT_FOUND);
        }
        Map<String, Object> snapshot = new HashMap<>();
        snapshot.put("name", user.getName());
        snapshot.put("beforeRole", user.getRole());
        return snapshot;
    }
}

