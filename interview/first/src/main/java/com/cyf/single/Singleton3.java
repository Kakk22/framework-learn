package com.cyf.single;

/**
 * @author 陈一锋
 * @date 2021/1/17 20:06
 **/
public class Singleton3 {

    /**
     * 懒汉写法:
     * 1、使用内部类,当访问内部类时 内部类才初始化属性
     * 2、线程安全,Jvm在加载类时内部保证线程安全
     */
    private Singleton3() {
    }

    public Singleton3 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton3 INSTANCE = new Singleton3();
    }
}
