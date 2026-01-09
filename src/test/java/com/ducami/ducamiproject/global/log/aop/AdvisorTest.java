package com.ducami.ducamiproject.global.log.aop;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdvisorTest {


    @Autowired
    private List<Advisor> advisors;

    @Test
    void findContext() {

    }

    @Test
    void test() {
        for (Advisor advisor : advisors) {
            System.out.println("==============");
            System.out.println(advisor);
        }
    }
}
