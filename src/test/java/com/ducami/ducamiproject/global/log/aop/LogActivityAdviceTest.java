package com.ducami.ducamiproject.global.log.aop;


import com.ducami.ducamiproject.domain.admin.log.enums.AdminAction;
import com.ducami.ducamiproject.domain.admin.log.enums.TargetType;
import com.ducami.ducamiproject.infra.log.annotation.LogActivity;
import com.ducami.ducamiproject.infra.log.aop.LogActivityAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LogActivityAdviceTest {

    @Autowired
    private LogActivityAdvice logActivityAdvice;

    public static class TestClass {
        @LogActivity(
            target = TargetType.USER,
            action = AdminAction.APPROVE,
            template = "new"
        )
        public void call() {}
    }

    @Test
    void testObjectProvider() {
        TestClass test = new TestClass();
        TestClass proxy1;
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(test);
        proxyFactory.addAdvice(logActivityAdvice);
        proxy1 = (TestClass) proxyFactory.getProxy();
        proxy1.call();


    }

//    @Test
//    @DisplayName("advice의 enrichers가 제대로 작동되는지 | MessageEnricher만")
//    void TestAfterEnrich() {
//        List<ContextEnricher> enrichers = List.of(new LogMessageEnricher());
//        TestAdvice newAdvice = new TestAdvice(null, null, enrichers);
//
//        Map<String, Object> params = Map.of("user", "하이");
//        LogActivityContext context = LogActivityContext.builder()
//                .template("{user}안녕하시고")
//                .params(params)
//                .build();
//
//        newAdvice.enrich(context);
//
//        assertThat(context).isNotNull();
//        assertThat(context.getMessage())
//                .isNotBlank()
//                .isEqualTo("하이안녕하시고");
//    }

}