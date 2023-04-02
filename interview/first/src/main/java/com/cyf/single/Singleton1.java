package com.cyf.single;

/**
 * @author 陈一锋
 * @date 2021/1/17 20:01
 **/
public class Singleton1 {
    /**
     * 饿汉写法
     * 1、线程安全
     * 2、加载类即创建实例
     * 3、构造器私有
     */
    public static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {

    }
}
