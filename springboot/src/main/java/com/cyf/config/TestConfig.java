package com.cyf.config;

import com.cyf.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈一锋
 * @date 2022/5/14 8:07 下午
 */
@Configuration
public class TestConfig {

    @Bean
    public HelloService helloService(){
        HelloService helloService = new HelloService();
        helloService.t1();
        return helloService;
    }
}
