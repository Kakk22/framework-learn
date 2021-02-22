package com.cyf.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS demo样例
 * <p>
 * 线程A抢占锁20秒
 * 线程B 和线程C 需要排队等候(构成双向队列 以Node为载体 Node内部维护了next和pre以及thread)
 * 线程A调用unlock方法后会将AQS中的state变成0表示无占用
 * 然后再调用LockSupport.unpark(A的下一个线程) 唤醒进行抢占锁
 *
 * @author 陈一锋
 * @date 2021/2/8 13:27
 **/
public class AQSDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("A get the lock");
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("B get the lock");
            } finally {
                lock.unlock();
            }
        }, "B").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("C get the lock");
            } finally {
                lock.unlock();
            }
        }, "C").start();
    }
}
