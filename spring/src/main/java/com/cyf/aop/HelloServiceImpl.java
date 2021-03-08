package com.cyf.aop;

/**
 * @author 陈一锋
 * @date 2021/2/12 20:34
 **/
public class HelloServiceImpl implements HelloService {

    @Override
    public void hello(){
        System.out.println("hello");
    }
}
