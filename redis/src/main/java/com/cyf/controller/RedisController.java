package com.cyf.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 陈一锋
 * @date 2022/3/19 11:02 上午
 */
@RestController
public class RedisController {

    @Resource
    private StringRedisTemplate template;

    @RequestMapping("/sendMsg/{id}")
    public void sendMessage(@PathVariable String id) {
        for (int i = 0; i < 10; i++) {
            template.convertAndSend("channel:cleanCache:config", "清除配置:" + i);
        }
    }
}
