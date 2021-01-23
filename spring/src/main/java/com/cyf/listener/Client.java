package com.cyf.listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 陈一锋
 * @date 2021/1/22 10:51
 **/
public class Client {

    public static void main(String[] args) {
        // 1. 初始化容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        // 2. 创建自定义事件
        TestEvent testEvent = new TestEvent("myEvent");
        //发布事件
        context.publishEvent(testEvent);
    }
}
