package com.ducami.ducamiproject.global.security.resolver;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContext;

@SpringBootTest
class SpringSecurityActorProviderTest {

    private final TestActorProvider actorProvider = new TestActorProvider();


    @Test
    @WithSecurityContext
    void getActor() {
        actorProvider.getUserDetails();
    }

}

