package com.cyf.jvm.ref;

import java.lang.ref.SoftReference;

/**
 * 软引用内存充足不会被回收
 * 常用缓存 回收
 *
 * @author 陈一锋
 * @date 2021/1/28 20:59
 **/
public class SoftReferenceDemo {

    /**
     * 软引用内存充足不会被回收
     */
    public static void softRefMemoryEnough() {
        Object o1 = new Object();
        SoftReference softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(softReference.get());
    }

    /**
     * 配置小内存  new 大对象
     */
    public static void softRefMemoryNotEnough() {
        Object o1 = new Object();
        SoftReference softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;

        try {
            // 这里会oom
            byte[] bytes = new byte[10 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // oom后软引用会被回收
            System.out.println(o1);
            System.out.println(softReference.get());
        }

        System.out.println(o1);
        System.out.println(softReference.get());
    }

    public static void main(String[] args) {
        // 软引用内存充足不会被回收
        //softRefMemoryEnough();
        // 软引用内存不足
        softRefMemoryNotEnough();
    }
}
