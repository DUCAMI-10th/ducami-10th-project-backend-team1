package com.ducami.ducamiproject.domain.user.service;

import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.user.entity.UserEntity;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AuthException(AuthStatusCode.ALREADY_EXISTS);
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserInfoResponse getUserInfo(String email) {
        UserEntity user = findByEmail(email)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
        return UserInfoResponse.from(user);
    }

    @Override
    @Transactional
    public void updateUserRole(Long id, UserRole userRole) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
        user.setRole(userRole);
        userRepository.save(user);
    }
}
