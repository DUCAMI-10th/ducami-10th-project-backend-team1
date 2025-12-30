package com.ducami.ducamiproject.domain.user.service;

import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.exception.UserException;
import com.ducami.ducamiproject.domain.user.exception.UserStatusCode;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
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
    public void updateUserRole(Long id, UserRole userRole) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
        user.setRole(userRole);
        userRepository.save(user);
    }
}
