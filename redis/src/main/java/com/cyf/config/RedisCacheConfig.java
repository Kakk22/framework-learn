package com.cyf.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2022/3/19 10:53 上午
 */
@EnableCaching
@Configuration
public class RedisCacheConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory, List<MessageListener> messageListenerList) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        for (MessageListener listener : messageListenerList) {
            container.addMessageListener(listener, new PatternTopic("channel:cleanCache:config"));
        }
        return container;
    }


    @Bean
    StringRedisTemplate redisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
}
