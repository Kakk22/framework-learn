package com.cyf.waitnotify;

/**
 * @author 陈一锋
 * @date 2021/2/3 20:46
 **/
public class SyncDemo {

    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName()+"\t come in");
                try {
                    System.out.println(Thread.currentThread().getName()+"\t 等待");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t 被唤醒");
            }
        }).start();

        new Thread(() -> {
            synchronized (lock) {
                lock.notifyAll();
                System.out.println(Thread.currentThread().getName()+"\t 唤醒线程");
            }
        }).start();
    }
}
