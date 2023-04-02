package com.cyf.domain;


import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈一锋
 * @date 2021/2/9 21:57
 **/
public class A implements AService{

    private B b;

    public A() {
        System.out.println("A 被创建了");
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }


    @Transactional
    @Override
    public void hello(){
        System.out.println("----");
    }
}
