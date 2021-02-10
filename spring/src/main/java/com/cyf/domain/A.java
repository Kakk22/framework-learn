package com.cyf.domain;

/**
 * @author 陈一锋
 * @date 2021/2/9 21:57
 **/
public class A {

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
}
