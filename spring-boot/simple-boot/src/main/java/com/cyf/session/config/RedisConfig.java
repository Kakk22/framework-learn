//package com.cyf.session.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//
//import java.time.Duration;
//
///**
// * @author 陈一锋
// * @date 2022/3/10 9:11 下午
// */
//@EnableRedisHttpSession
//public class RedisConfig {
//
//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setDatabase(0);
//        redisStandaloneConfiguration.setHostName("127.0.0.1");
//        redisStandaloneConfiguration.setPort(6379);
////        redisStandaloneConfiguration.setPassword(RedisPassword.of());
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .commandTimeout(Duration.ofMillis(30000))
//                .shutdownTimeout(Duration.ofMillis(30000))
////                .poolConfig(genericObjectPoolConfig)
//                .build();
//
//        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
//    }
//}
