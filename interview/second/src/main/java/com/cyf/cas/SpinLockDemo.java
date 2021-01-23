package com.cyf.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁案例
 *
 * @author 陈一锋
 * @date 2021/1/23 9:40
 **/
public class SpinLockDemo {

    /**
     * 原子引用线程
     */
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();


    public void lock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + "没有获取到锁,一直自旋继续获取");
        }
        System.out.println(thread.getName() + "获取到锁");
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "解锁");
    }


    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.lock();
            //获取锁后睡眠1秒
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        }, "AA").start();

        //主线程休眠1秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.lock();
            spinLockDemo.unLock();
        }, "BB").start();
    }
}
