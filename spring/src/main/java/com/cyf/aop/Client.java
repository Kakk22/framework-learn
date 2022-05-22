package com.cyf.aop;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

/**
 * @author 陈一锋
 * @date 2021/2/12 20:48
 **/
public class Client {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
//        HelloService helloService = (HelloService) context.getBean("helloService");
//        helloService.hello();
        Object a = context.getBean("a");
        System.out.println("---");

    }
}
