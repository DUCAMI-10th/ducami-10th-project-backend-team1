package com.ducami.ducamiproject.domain.user.domain;

import com.ducami.ducamiproject.domain.enums.UserRole;
import com.ducami.ducamiproject.global.entity.Base;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity extends Base {

    @Column
    private String password;

    @Column
    private Integer grade;

    @Column
    private String name;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private Integer generation;

    @Column
    private String major;

}
