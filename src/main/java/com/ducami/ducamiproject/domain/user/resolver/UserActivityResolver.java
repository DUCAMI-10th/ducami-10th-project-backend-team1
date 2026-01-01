package com.ducami.ducamiproject.domain.user.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.admin.log.resolver.DefaultActivityResolver;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.exception.UserException;
import com.ducami.ducamiproject.domain.user.exception.UserStatusCode;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserActivityResolver extends DefaultActivityResolver {
    private final UserRepository userRepository;

    @Override
    public boolean supports(TargetType target) {
        return TargetType.USER == target;
    }

    // Long을 array로 받아서 저장해도 좋을듯 이때 LogTargetPK의 이름을 붙여줘서 Map형태로 저장해도 좋을거 같음
    @Override
    public Map<String, Object> before(Long id) {
        Map<String, Object> result = new HashMap<>();
        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));

        result.put("name", user.getName());
        result.put("beforeRole", user.getRole());
        return result;
    }
}
