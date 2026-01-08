package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import com.ducami.ducamiproject.global.log.resolver.LogActivityResolver;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogActivityAdviceTest {

    @Autowired
    private UserService userService;

    public static class AdviceTest extends LogActivityAdvice {
        public AdviceTest(List<LogActivityResolver> resolvers) {
            super(resolvers);
        }
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Object result = super.invoke(invocation);

            return result; // 이거 테스트는 save로직 만들고 거기에 들어가는걸 mock으로 해서 테스트하는걸로
        }
    }

    //advice 하나 extends해서 안에 필드넣고 그걸로 테스트해보는걸로

    @DisplayName("userService Context 생성 테스트")
    @Test
    void Test_userServiceContext() {
        userService.updateUserRole(4L, UserRole.ROLE_CLUB);
    }

}