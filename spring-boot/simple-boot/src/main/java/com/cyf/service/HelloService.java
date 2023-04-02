package com.cyf.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈一锋
 * @date 2021/1/27 11:38
 **/
//@Service
public class HelloService {

    public HelloService() {
        System.out.println("hello service 构造器");
    }

    @Transactional
    public void t1(){
        System.out.println("t1...");
    }
}
