package com.cyf.juc.practice;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个线程轮流打印
 * A打印5次
 * B打印10次
 * C打印15次
 * --------
 * A打印5次 继续
 *
 * @author 陈一锋
 * @date 2021/1/24 14:53
 **/
public class LockPractice {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            while (true) {
                data.print5();
            }
        }, "AAA").start();

        new Thread(() -> {
            while (true) {
                data.print10();
            }
        }, "BBB").start();

        new Thread(() -> {
            while (true) {
                data.print15();
            }
        }, "CCC").start();
    }
}

/**
 * 资源类
 */
class Data {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                c1.await();
            }
            TimeUnit.SECONDS.sleep(5);
            for (int i = 0; i < 5; i++) {
                System.out.println("i am aaa");
            }
            number = 2;
            // 唤醒b线程
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("i am bbb");
            }
            number = 3;
            // 唤醒c线程
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println("i am ccc");
            }
            number = 1;
            // 唤醒a线程
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}