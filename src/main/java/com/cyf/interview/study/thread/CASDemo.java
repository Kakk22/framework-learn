package com.cyf.interview.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author by cyf
 * @date 2020/7/5.
 *
 * CAS 是什么？ == compareAndSwap
 * 比较并交换
 * 类似于乐观锁
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2020)+"\t current data:"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2021)+"\t current data:"+atomicInteger.get());
    }
}
