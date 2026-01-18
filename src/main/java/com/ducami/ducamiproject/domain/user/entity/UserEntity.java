package com.ducami.ducamiproject.domain.user.entity;

import com.ducami.ducamiproject.domain.user.dto.request.UpdateUserRequest;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.global.entity.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserEntity extends Base {

    @Column
    private String password;

    @Column
    private Integer grade;

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column
    private Integer classNumber;

    @Column
    private Integer number;

    @Setter
    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private Integer generation;


    public void setStudentId(String studentId) {
        if (studentId.isBlank() || studentId.length() != 4 || studentId.startsWith("0")) {
            throw new IllegalArgumentException("Invalid student id: " + studentId);
        }
        grade = Integer.parseInt(studentId.substring(0, 1));
        classNumber = Integer.parseInt(studentId.substring(1, 2));
        number = Integer.parseInt(studentId.substring(2));
    }

    public String getStudentId() {
        try {
            return "" + grade + classNumber + number;
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public void updateUserInfo(UpdateUserRequest request) {
        if (request.email() != null) {email = request.email();}
        if (request.generation() != null) {generation = request.generation();}
        if (request.studentId() != null) {setStudentId(request.studentId());}
    }
}
