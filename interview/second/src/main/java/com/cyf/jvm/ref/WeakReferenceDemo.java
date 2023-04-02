package com.cyf.jvm.ref;

import java.lang.ref.WeakReference;

/**
 * 弱引用 无论内存是否充足
 * 垃圾回收都会回收该对象
 *
 * @author 陈一锋
 * @date 2021/1/28 21:09
 **/
public class WeakReferenceDemo {

    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(weakReference.get());

    }
}
