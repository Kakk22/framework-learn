package com.cyf.juc.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印
 * A打印1
 * B打印2
 * 打印到10停止
 *
 * @author 陈一锋
 * @date 2021/1/24 17:23
 **/
public class AlternatelyPrint {
    public static void main(String[] args) {
        Resource resource = new Resource();

        new Thread(() ->
                resource.printOdd()
                , "A").start();
        new Thread(() ->
                resource.printEven()
                , "B").start();
    }
}

/**
 * 资源类
 */
class Resource {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private static final int STOP = 100;

    /**
     * 打印奇数
     */
    public void printOdd() {
        lock.lock();
        try {
            while (number < STOP) {
                while (number % 2 == 0) {
                    c1.await();
                }
                System.out.println(Thread.currentThread().getName() + " print " + number);
                number++;
                c2.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 打印偶数
     */
    public void printEven() {
        lock.lock();
        try {
            while (number < STOP) {
                while (number % 2 == 1) {
                    c2.await();
                }
                System.out.println(Thread.currentThread().getName() + " print " + number);
                number++;
                c1.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}