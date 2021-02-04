package org.simple.bootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class BootDemoApplicationTests {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Test
    void contextLoads() {
        System.out.println(threadPoolExecutor.getCorePoolSize());
    }

}
