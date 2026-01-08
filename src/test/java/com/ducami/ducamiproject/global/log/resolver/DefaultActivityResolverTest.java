package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.domain.user.domain.UserEntity;
import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.global.log.annotation.LogActivity;
import com.ducami.ducamiproject.global.log.annotation.target.LogTargetEntity;
import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import com.ducami.ducamiproject.global.log.aop.source.AnnotationLogActivitySource;
import com.ducami.ducamiproject.global.log.aop.source.LogActivitySource;
import org.aopalliance.intercept.Invocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.introspection.ClassUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
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
        private LogActivity logActivity;
        private final LogActivitySource source = new AnnotationLogActivitySource();

        public MethodInvocation getInvocation() {
            return invocation;
        }
        public LogActivity getLogActivity() {
            return logActivity;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            logActivity = source.getLogActivity(invocation.getMethod());
            this.invocation = invocation;
            return invocation.proceed();
        }

    }

    public interface TestInterface {
        @LogActivity(
            target = TargetType.USER,
            action = AdminAction.APPROVE,
            template = "user.name: {user.name},  _res: {_res}, username: {username}"
        )
        String helloWorld(@LogTargetEntity("user") String username);
        String userSpace(String username);
    }

    public static class TestClass implements TestInterface {
        @Override
        public String helloWorld(String username) {
            return "hello world";
        }
        @LogActivity(
                target = TargetType.USER,
                action = AdminAction.APPROVE,
                template = "user.name: {user.name},  _res: {_res}, username: {username}"
        )
        @Override
        public String userSpace(@LogTargetEntity("userEntity") String username) {
            return "userSpace";
        }
    }

    @Test
    @DisplayName("extractParams 테스트")
    void extractParamsTest() throws NoSuchMethodException {
        TestClass testClass = new TestClass();
        Method method = AopUtils.getTargetClass(testClass).getDeclaredMethod("helloWorld", String.class);
        Object[] args = {"엄준식"};

        Map<String, Object> params = resolver.extractParams(method, args);
        assertThat(params.getOrDefault("username", null)).isEqualTo("엄준식");
    }

    @Test
    @DisplayName("Advice resolver 테스트")
    void testAdviceResolver() throws Throwable {
        // ProxyFactory를 통해 advice를 실행시켜 MethodInvocation를 얻음
        Advice advice = new Advice();
        TestClass testClass = new TestClass();
        TestInterface proxy1;

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvice(advice);
        proxyFactory.setTarget(testClass);

        proxy1 = (TestInterface) proxyFactory.getProxy();
        String res = proxy1.helloWorld("test resolver");
        // == Advisor -> Pointcut -> Advice -> invoke ==

        assertThat(advice.getInvocation())
                .isInstanceOf(MethodInvocation.class);
        assertThat(advice.getLogActivity())
                .isInstanceOf(LogActivity.class);

        LogActivityContext context = resolver.resolve(advice.invocation, advice.logActivity);

        assertThat(context.getTarget()).isEqualTo(TargetType.USER);
        assertThat(context.getAction()).isEqualTo(AdminAction.APPROVE);
        assertThat(context.getTemplate()).isEqualTo("user.name: {user.name},  _res: {_res}, username: {username}");
        assertThat(context.getProceed()).isEqualTo("hello world");

        System.out.println("=======");
        Class<?> clazz = resolver.getTargetClass(advice.invocation);
        Method method = resolver.getMethod(advice.invocation, clazz);
        System.out.println(clazz);
        System.out.println(method);
        System.out.println("=======");
        System.out.println(proxy1.getClass());
        System.out.println(advice.invocation.getThis());
        System.out.println(advice.invocation.getClass().getName());
        System.out.println(AopUtils.getTargetClass(advice.invocation.getThis()).getName());
        System.out.println(AopUtils.getMostSpecificMethod(advice.invocation.getMethod(), AopUtils.getTargetClass(advice.invocation.getClass())));

        System.out.println("=======");
        System.out.println(ClassUtils.getAllInterfaces(clazz));
        method = ClassUtils.getAllInterfaces(clazz).get(0).getMethod("helloWorld", String.class);
        for (int i = 0; i < method.getParameterCount(); i++) {
            MethodParameter mp = new MethodParameter(method, i);
            System.out.println(Arrays.toString(mp.getParameterAnnotations()));
        }
    }


    @Test
    @DisplayName("getTargetId 테스트")
    void testGetTargetId() throws NoSuchMethodException {
        Class<?> clazz = TestClass.class;
        Method method1 = clazz.getDeclaredMethod("helloWorld", String.class);
        Method method2 = clazz.getDeclaredMethod("userSpace", String.class);
        Object[] args = {"엄준식"};

        Map<String, Object> targetIds1 = resolver.getTargetId(clazz, method1, args);
        Map<String, Object> targetIds2 = resolver.getTargetId(clazz, method2, args);

        assertThat(targetIds1)
                .containsKey("user")
                .containsValue("엄준식")
                .hasSize(1);
        assertThat(targetIds2)
                .containsKey("userEntity")
                .containsValue("엄준식")
                .hasSize(1);

    }
}