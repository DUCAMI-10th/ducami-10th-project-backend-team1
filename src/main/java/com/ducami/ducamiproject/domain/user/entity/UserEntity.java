package com.ducami.ducamiproject.domain.user.entity;

import com.ducami.ducamiproject.domain.admin.log.entity.AdminLogEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.global.entity.Base;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private int grade;

    @Column
    private String name;

    @Setter
    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private int generation;

    @Column
    private String major;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actor")
    private List<AdminLogEntity> logs = new ArrayList<>();

}
