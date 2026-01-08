package com.ducami.ducamiproject.global.log.resolver;

import com.ducami.ducamiproject.global.log.aop.LogActivityContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpELMessageResolverTest {

    @Autowired
    private LogMessageResolver resolver;

    @Test
    @DisplayName("메세지 렌더 테스트1")
    void Test_userServiceContext1() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "엄준식");
        params.put("jett", "제트");
        params.put("interface", Map.of("monster", "몬스터"));

        LogActivityContext context = LogActivityContext.builder()
            .template("{name}과 {jett}가 싸워서 {interface.monster}이 변경되었어요.")
            .params(params)
            .build();

        String expected = "엄준식과 제트가 싸워서 몬스터이 변경되었어요.";
        String result = resolver.render(context);

        Assertions.assertEquals(expected, result);
    }

}