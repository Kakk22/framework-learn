package com.cyf.producerAndConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程生产者消费者模型
 * 题目:一个初始值为0的变量，两个线程对其进行交替操作，一个+1,一个-1, 一共5轮
 * 1、线程 操作 资源类
 * 2、判断 干活 通知
 * 3、防止虚假唤醒 多线程判断用while
 *
 * @author 陈一锋
 * @date 2021/1/24 12:02
 **/
public class LockDemo {
    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                data.increment();
            }
        },"AAA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                data.decrement();
            }
        },"BBB").start();
    }
}


/**
 * 资源类
 */
class Data {
    private int i;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            //1.判断
            while (i != 0) {
                //2.等待不能生产
                condition.await();
            }
            //3.操作
            i++;
            System.out.println(Thread.currentThread().getName() + "\t 操作后i值" + i);
            //4.唤醒
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            while (i == 0) {
                //等待不能消费
                condition.await();
            }
            i--;
            System.out.println(Thread.currentThread().getName() + "\t 操作后i值" + i);
            //唤醒
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}