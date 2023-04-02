package com.cyf.domain;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 陈一锋
 * @date 2021/2/9 22:02
 **/
public class Client {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        A a = (A) context.getBean("a");
        a.hello();
    }
}
