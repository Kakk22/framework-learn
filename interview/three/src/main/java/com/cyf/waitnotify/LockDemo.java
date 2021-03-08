package com.cyf.waitnotify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * await signal 等待唤醒
 *
 * @author 陈一锋
 * @date 2021/2/3 21:40
 **/
public class LockDemo {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("A come in");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A 被唤醒");
            } finally {
                lock.unlock();
            }
        }).start();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("B 唤醒 A");
                condition.signal();
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
