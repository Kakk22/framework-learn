package com.cyf.im.netty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈一锋
 * @date 2021/2/5 15:46
 **/
@Configuration
public class ConfigBeanConfiguration {

    @Bean
    @ConfigurationProperties("netty")
    public NettyProperties nettyProperties(){
        return new NettyProperties();
    }
}
