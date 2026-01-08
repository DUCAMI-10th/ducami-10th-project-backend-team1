package com.ducami.ducamiproject.global.security.resolver;


import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.global.entity.UserActor;
import com.ducami.ducamiproject.global.security.AuthenticationHelper;
import com.ducami.ducamiproject.global.security.annotation.MockUserEntity;
import com.ducami.ducamiproject.global.security.entity.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringSecurityActorProviderTest {

    private final TestActorProvider actorProvider = new TestActorProvider();

    @Autowired
    private AuthenticationHelper helper;

    @Test
    @MockUserEntity(id=2L, name="T1", email="T2", role= UserRole.ROLE_USER)
    void testMockUser() {
        UserEntity user = helper.getCurrentUser();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("T1");
        assertThat(user.getEmail()).isEqualTo("T2");
        assertThat(user.getRole()).isEqualTo(UserRole.ROLE_USER);
    }

    @Test
    @MockUserEntity(id=2L, name="엄준식", email="abc@a.com", role=UserRole.ROLE_ADMIN)
    void testActorGetUserDetails() {
        CustomUserDetails details = actorProvider.getUserDetails();

        assertThat(details).isNotNull()
            .isInstanceOf(CustomUserDetails.class);
        assertThat(details.getUsername()).isEqualTo("abc@a.com");

        UserActor actor = actorProvider.getActor();

        assertThat(actor.getUsername())
                .isEqualTo("엄준식");
        assertThat(actor.getUserId())
                .isEqualTo(2L);

    }

}

