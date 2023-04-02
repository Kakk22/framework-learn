package com.cyf.single;

/**
 * @author 陈一锋
 * @date 2021/1/17 20:03
 **/
public class Singleton2 {
    /**
     * 饿汉式写法：
     * 1、构造器私有
     * 2、访问时再创建
     * 3、双重判断+synchronized 保证线程安全
     */
    public volatile static Singleton2 INSTANCE;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton2();
                }
            }
        }
        return INSTANCE;
    }
}
