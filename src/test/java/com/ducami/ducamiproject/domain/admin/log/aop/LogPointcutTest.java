package com.ducami.ducamiproject.domain.admin.log.aop;


import com.ducami.ducamiproject.domain.admin.log.annotation.LogActivity;
import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.domain.user.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

class LogPointcutTest {

    private final UserService userService = new UserService() {

        public void test1() {

        }

        @LogActivity(
                target = TargetType.USER,
                action = AdminAction.UPDATE
        )
        public void test2() {

        }

        @Override
        @LogActivity(
            target = TargetType.USER,
            action = AdminAction.UPDATE
        )
        public void checkEmail(String email) {
        }

        @Override
        public Optional<UserEntity> findByEmail(String email) {
            return Optional.empty();
        }

        @Override
        public UserEntity save(UserEntity userEntity) {
            return null;
        }

        @Override
        public UserInfoResponse getUserInfo(String email) {
            return null;
        }

        @Override
        public void updateUserRole(Long id, UserRole userRole) {

        }
    };

    @DisplayName("특정 메서드의 구현체의 인터페이스가 LogActivity를 가지고 있는지 확인한다.")
    @Test
    void testLogPointcutIsTrue() throws Exception {
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

    @DisplayName("특정 메서드의 구현체의 인터페이스가 LogActivity를 가지고 있지 않은지 확인한다.")
    @Test
    void testLogPointcutIsFalse() throws Exception {
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

    @DisplayName("인터페이스에는 없지만, 구현체에는 Activity가 있는 경우")
    @Test
    void testLogPointcutIsTrueWhereImpl() {
        LogPointcut logPointcut = new LogPointcut();
        String testMethodName = "checkEmail";
        Method method = Arrays.stream(userService.getClass().getDeclaredMethods())
                .filter(m -> m.getName().equals(testMethodName))
                .findFirst()
                .orElse(null);

        org.junit.jupiter.api.Assertions.assertNotNull(method);
        Assertions.assertThat(logPointcut.matches(method, UserServiceImpl.class))
                .isTrue();
    }

    @DisplayName("인터페이스에는 없지만, 구현체에는 Activity가 없는 경우")
    @Test
    void testLogPointcutIsFalseWhereImpl() {
        LogPointcut logPointcut = new LogPointcut();
        String testMethodName = "findByEmail";
        Method method = Arrays.stream(userService.getClass().getDeclaredMethods())
                .filter(m -> m.getName().equals(testMethodName))
                .findFirst()
                .orElse(null);

        org.junit.jupiter.api.Assertions.assertNotNull(method);
        Assertions.assertThat(logPointcut.matches(method, UserServiceImpl.class))
                .isFalse();
    }

    @DisplayName("구현체에만 존재하는 메서드에 Activity가 있는 경우")
    @Test
    void testLogPointcutIsTrueWhereOnlyImpl() {
        LogPointcut logPointcut = new LogPointcut();
        String testMethodName = "test2";
        Method method = Arrays.stream(userService.getClass().getDeclaredMethods())
                .filter(m -> m.getName().equals(testMethodName))
                .findFirst()
                .orElse(null);

        org.junit.jupiter.api.Assertions.assertNotNull(method);
        Assertions.assertThat(logPointcut.matches(method, UserServiceImpl.class))
                .isTrue();
    }

    @DisplayName("구현체에만 존재하는 메서드에 Activity가 없는 경우")
    @Test
    void testLogPointcutIsFalseWhereOnlyImpl() {
        LogPointcut logPointcut = new LogPointcut();
        String testMethodName = "test1";
        Method method = Arrays.stream(userService.getClass().getDeclaredMethods())
                .filter(m -> m.getName().equals(testMethodName))
                .findFirst()
                .orElse(null);

        org.junit.jupiter.api.Assertions.assertNotNull(method);
        Assertions.assertThat(logPointcut.matches(method, UserServiceImpl.class))
                .isFalse();
    }
}