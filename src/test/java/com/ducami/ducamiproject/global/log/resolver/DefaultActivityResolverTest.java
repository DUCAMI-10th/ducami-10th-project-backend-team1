package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DefaultActivityResolverTest {

    private final DefaultActivityResolver resolver
            = new DefaultActivityResolver() {
        @Override
        public boolean supports(TargetType target) {
            return false;
        }

        @Override
        public Map<String, Object> before(Map<String, Object> targetIds) {
            return Map.of();
        }

        @Override
        public String resolve(Map<String, Object> params, String message) {
            return "";
        }

        @Override
        public Map<String, Object> toSnapshot(Object entity) {
            return Map.of();
        }
    };


    private static class Advice implements MethodInterceptor {
        private MethodInvocation invocation;

        public MethodInvocation getInvocation() {
            return invocation;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            this.invocation = invocation;
            return null;
        }
    }


    @Autowired
    private UserService userService;

    @Test
    @DisplayName("extractParams 테스트")
    void extractParamsTest() throws NoSuchMethodException {
      Method method = AopUtils.getTargetClass(userService).getDeclaredMethod("updateUserRole", Long.class, UserRole.class);
      Object[] args = {4L, UserRole.ROLE_USER};

      Map<String, Object> params = resolver.extractParams(method, args);
      Assertions.assertThat(params.getOrDefault("id", null)).isEqualTo(4L);
      Assertions.assertThat(params.getOrDefault("userRole", null)).isEqualTo(UserRole.ROLE_USER);
    }

    @Test
    @DisplayName("Advice resolver 테스트")
    void testAdviceResolver() {
        Advice advice = new Advice();
        UserService proxy1;
        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.addAdvice(advice);
        proxyFactory.setTarget(userService);

        proxy1 = (UserService) proxyFactory.getProxy();
        proxy1.updateUserRole(4L, UserRole.ROLE_USER);
        System.out.println(advice.getInvocation());

    }

}