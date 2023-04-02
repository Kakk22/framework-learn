package com.cyf.domain;

/**
 * @author 陈一锋
 * @date 2021/2/9 21:58
 **/
public class B {

    private A a;

    public B() {
        System.out.println("B被创建了");
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
