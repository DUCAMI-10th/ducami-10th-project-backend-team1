package com.ducami.ducamiproject.domain.user.service;

import com.ducami.ducamiproject.domain.auth.exception.AuthException;
import com.ducami.ducamiproject.domain.auth.exception.AuthStatusCode;
import com.ducami.ducamiproject.domain.user.dto.request.UpdateUserRequest;
import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.exception.UserException;
import com.ducami.ducamiproject.domain.user.exception.UserStatusCode;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AuthException(AuthStatusCode.ALREADY_EXISTS_EMAIL);
        }
    }

    @Override
    public void checkUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AuthException(AuthStatusCode.ALREADY_EXISTS_USERNAME);
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUser(String username, UpdateUserRequest request) {
        UserEntity user = findByUsername(username)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
        if (request.email() != null && !user.getEmail().equals(request.email())) {
            checkEmail(request.email());
        }
        user.updateUserInfo(request);
    }

    @Override
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public void updateUserRole(Long id, UserRole userRole) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserStatusCode.NOT_FOUND));
        user.setRole(userRole);
        userRepository.save(user);
    }
}
