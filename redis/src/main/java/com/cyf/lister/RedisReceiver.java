package com.cyf.lister;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author 陈一锋
 * @date 2022/3/19 10:49 上午
 */
@Component
public class RedisReceiver implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("接收到消息,消息体:"+new String(message.getBody()));
        System.out.println("接收到消息,管道:"+new String(message.getChannel()));
    }
}
