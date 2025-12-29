package com.ducami.ducamiproject.domain.auth.dto.response;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
    String name;
    String email;
    Integer grade;
    Integer generation;
    String major;
    UserRole role;

    public static UserInfoResponse from(UserEntity entity) {
        return new UserInfoResponse(entity.getName(),
                entity.getEmail(),
                entity.getGrade(),
                entity.getGeneration(),
                entity.getMajor(),
                entity.getRole()
        );
    }
}
