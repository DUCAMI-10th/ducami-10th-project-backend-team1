package com.ducami.ducamiproject.domain.user.dto.response;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
    Long id;
    String name;
    String email;
    Integer grade;
    Integer generation;
    String major;
    UserRole role;

    public static UserInfoResponse from(UserEntity entity) {
        return new UserInfoResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getGrade(),
                entity.getGeneration(),
                entity.getMajor(),
                entity.getRole()
        );
    }
}
