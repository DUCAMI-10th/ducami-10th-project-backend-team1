package com.ducami.ducamiproject.domain.admin.log.domain;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.global.entity.Base;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_log")
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String actionType;

    @Column
    private String details;

    @Column
    @CreationTimestamp
    private LocalDateTime actionTime;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private UserEntity actor;
}
