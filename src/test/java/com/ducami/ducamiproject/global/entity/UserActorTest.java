package com.ducami.ducamiproject.global.entity;

import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserActorTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("userActor 'of' non id")
    void ofEntity1() {
        UserEntity user = UserEntity.builder()
                .name("테스트 엄준식")
                .build();
        UserActor actor = UserActor.of(user);

        assertThat(actor.getUserId())
                .isNull();
        assertThat(actor.getUsername())
                .isEqualTo("테스트 엄준식");
    }

    @Test
    @DisplayName("userActor")
    void ofEntity2() {

        UserActor actor = new UserActor(1L, "테스트 엄준식");

        assertThat(actor.getUserId())
                .isEqualTo(1L);
        assertThat(actor.getUsername())
                .isEqualTo("테스트 엄준식");
    }

    @Test
    @DisplayName("userActor using repo")
    void ofEntity3() {
        UserEntity user = UserEntity.builder().name("task").role(UserRole.ROLE_USER).email("test@test.com").build();
        UserEntity saved = userRepository.save(user);
        UserActor actor = UserActor.of(saved);

        assertThat(actor.getUserId())
                .isEqualTo(saved.getId());
        assertThat(actor.getUsername())
                .isEqualTo(saved.getName());
    }

}