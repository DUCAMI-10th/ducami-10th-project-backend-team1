package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.infra.log.annotation.LogActivity;
import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.entity.UserEntity;
import com.ducami.ducamiproject.domain.user.dto.response.UserInfoResponse;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.infra.log.aop.LogActivityAdvice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class LogActivityAutoConfigurationTest {

    @Autowired
    private Advisor logActivityAdvisor;

    private final UserService userService = new UserService() {

        @Override
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

    public interface TestClass {
        @LogActivity(
            target = TargetType.USER,
            action = AdminAction.APPROVE
        )
        String method1();

        String method2(String email);

        @LogActivity(
                target = TargetType.USER,
                action = AdminAction.APPROVE
        )
        String method3(String userName);
    }

    public static class TestClass1 implements TestClass {
        @Override
        public String method1() {
            return "method1";
        }
        @Override
        @LogActivity(
                target = TargetType.USER,
                action = AdminAction.APPROVE
        )
        public String method2(String email) {
            return "method-1";
        }

        @Override
        @LogActivity(
                target = TargetType.USER,
                action = AdminAction.CREATE
        )
        public String method3(String userName) {
            return "method--1";
        }
    }

    public static class TestClass2 implements TestClass {
        @Override
        public String method1() {
            return "method2";
        }
        @Override
        public String method2(String email) {
            return "method-2";
        }
        @Override
        public String method3(String userName) {
            return "method-2";
        }
    }

    @DisplayName("커스텀 advisor")
    @Test
    void testAdvisorBean() {
        Assertions.assertThat(logActivityAdvisor.getAdvice().getClass())
                .isEqualTo(LogActivityAdvice.class);
    }

    @DisplayName("Bean에 등록된 Advisor 실행 테스트")
    @Test
    void testBeanAdvisor() {
        TestClass2 testClass2 = new TestClass2();
        TestClass1 testClass1 = new TestClass1();
        TestClass proxy1;
        TestClass proxy2;

        // PROXY 1
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(logActivityAdvisor);
        proxyFactory.setTarget(testClass1);

        proxy1 = (TestClass) proxyFactory.getProxy();
        System.out.println(proxy1.method1());
        System.out.println(proxy1.method2("b"));
        System.out.println(proxy1.method3("엄준식"));

        // PROXY 2
        proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(logActivityAdvisor);
        proxyFactory.setTarget(testClass2);

        proxy2 = (TestClass) proxyFactory.getProxy();
        System.out.println(proxy2.method1());
        System.out.println(proxy2.method2("b"));
        System.out.println(proxy2.method3("엄준식"));

    }
}