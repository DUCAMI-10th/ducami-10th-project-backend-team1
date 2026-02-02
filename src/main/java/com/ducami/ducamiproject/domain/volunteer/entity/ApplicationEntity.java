package com.ducami.ducamiproject.domain.volunteer.entity;

import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "volunteerApplication")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter

public class ApplicationEntity {
    @Id
    @Column(name = "application_id", nullable = false)
    private long id;

    @Column(name = "application_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, length = 1500)
    private String content;

    // 아직 이벤트 없음
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "e_id", nullable = false)
//    private event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id", nullable = false)
    private UserEntity user;

    public void updateStatus(String status){
        this.status = status;
    }
}
