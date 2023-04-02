package com.cyf.waitnotify;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 陈一锋
 * @date 2021/2/6 20:24
 **/
public class LockSupportDemo {
    public static void main(String[] args) {

        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            //堵塞
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t 被唤醒");
        }, "A");
        a.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 唤醒A");
            //唤醒
            LockSupport.unpark(a);
        }, "B").start();
    }
}
