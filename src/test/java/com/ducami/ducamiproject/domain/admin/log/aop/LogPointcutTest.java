package com.ducami.ducamiproject.domain.admin.log.aop;


import com.ducami.ducamiproject.domain.user.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

class LogPointcutTest {

    @DisplayName("PointCut 테스트. 특정 메서드의 구현체의 인터페이스가 LogActivity를 가지고 있는지 확인한다.")
    @Test
    void testLogPointcut1() throws Exception {
        LogPointcut logPointcut = new LogPointcut();
        String testMethodName = "updateUserRole";
        Method method = Arrays.stream(UserServiceImpl.class.getDeclaredMethods())
                .filter(m -> m.getName().equals(testMethodName))
                .findFirst()
                .orElse(null);

        org.junit.jupiter.api.Assertions.assertNotNull(method);
        Assertions.assertThat(logPointcut.matches(method, UserServiceImpl.class))
            .isTrue();
    }

    @DisplayName("PointCut 테스트. 특정 메서드의 구현체의 인터페이스가 LogActivity를 가지고 있지 않은지 확인한다.")
    @Test
    void testLogPointcut2() throws Exception {
        LogPointcut logPointcut = new LogPointcut();
        String testMethodName = "checkEmail";
        Method method = Arrays.stream(UserServiceImpl.class.getDeclaredMethods())
                .filter(m -> m.getName().equals(testMethodName))
                .findFirst()
                .orElse(null);

        org.junit.jupiter.api.Assertions.assertNotNull(method);
        Assertions.assertThat(logPointcut.matches(method, UserServiceImpl.class))
                .isFalse();
    }

}