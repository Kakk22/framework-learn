package com.cyf.jvm.ref;

/**
 * 强引用即使内存不足 不会被垃圾回收
 *
 * @author 陈一锋
 * @date 2021/1/28 20:52
 **/
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object();
        // obj2 为指向new Object()的强引用 不会被垃圾回收
        Object obj2 = obj1;

        obj1 = null;
        System.gc();

        System.out.println(obj2);
    }
}
