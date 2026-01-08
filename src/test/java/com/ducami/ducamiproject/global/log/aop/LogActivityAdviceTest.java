package com.ducami.ducamiproject.global.log.aop;


import com.ducami.ducamiproject.global.log.enricher.ContextEnricher;
import com.ducami.ducamiproject.global.log.enricher.LogMessageEnricher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LogActivityAdviceTest {

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