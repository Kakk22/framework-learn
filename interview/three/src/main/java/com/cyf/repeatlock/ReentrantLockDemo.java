package com.cyf.repeatlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈一锋
 * @date 2021/2/3 20:16
 **/
public class ReentrantLockDemo {

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> t1()).start();
        new Thread(() -> t1()).start();
        new Thread(() -> t1()).start();
        //Thread-0	 执行t1方法
        //Thread-0	 执行t2方法
        //Thread-2	 执行t1方法
        //Thread-2	 执行t2方法
        //Thread-1	 执行t1方法
        //Thread-1	 执行t2方法
    }

    static void t1() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 执行t1方法");
            t2();
        } finally {
            lock.unlock();
        }
    }

    static void t2() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 执行t2方法");
        } finally {
            lock.unlock();
        }
    }
}
