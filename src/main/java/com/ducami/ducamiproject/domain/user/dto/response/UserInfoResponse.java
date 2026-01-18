package com.ducami.ducamiproject.domain.user.dto.response;

import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
    Long id;
    String username;
    String email;
    Integer generation;
    UserRole role;
    String studentId;

    public static UserInfoResponse from(UserEntity entity) {
        return new UserInfoResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getGeneration(),
                entity.getRole(),
                entity.getStudentId()
        );
    }
}
