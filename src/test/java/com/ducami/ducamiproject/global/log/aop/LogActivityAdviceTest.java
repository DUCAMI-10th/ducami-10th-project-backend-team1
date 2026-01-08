package com.ducami.ducamiproject.global.log.aop;

import com.ducami.ducamiproject.domain.user.enums.UserRole;
import com.ducami.ducamiproject.domain.user.service.UserService;
import com.ducami.ducamiproject.global.log.enricher.ContextEnricher;
import com.ducami.ducamiproject.global.log.enricher.LogMessageEnricher;
import com.ducami.ducamiproject.global.log.resolver.LogActivityResolver;
import com.ducami.ducamiproject.global.log.sink.LogActivitySink;
import org.aopalliance.aop.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogActivityAdviceTest {

    public static class TestAdvice extends LogActivityAdvice {
        public TestAdvice(List<LogActivityResolver> resolvers, LogActivitySink sink, List<ContextEnricher> enrichers) {
            super(resolvers, sink, enrichers);
        }

        int getEnricherCount() {
            return enrichers.size();
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private LogActivityAdvice advice;

    @Test
    void test() {
        assertThat(advice).isNotNull();
    }

    @Test
    void testEnricherSize() {
        List<ContextEnricher> enrichers = List.of(new LogMessageEnricher());
        TestAdvice newAdvice = new TestAdvice(null, null, enrichers);

        assertThat(newAdvice.getEnricherCount()).isEqualTo(enrichers.size());
    }

    @Test
    @DisplayName("advice의 enrichers가 제대로 작동되는지 | MessageEnricher만")
    void TestAfterEnrich() {
        List<ContextEnricher> enrichers = List.of(new LogMessageEnricher());
        TestAdvice newAdvice = new TestAdvice(null, null, enrichers);

        Map<String, Object> params = Map.of("user", "하이");
        LogActivityContext context = LogActivityContext.builder()
                .template("{user}안녕하시고")
                .params(params)
                .build();

        newAdvice.enrich(context);

        assertThat(context).isNotNull();
        assertThat(context.getMessage())
                .isNotBlank()
                .isEqualTo("하이안녕하시고");
    }

}