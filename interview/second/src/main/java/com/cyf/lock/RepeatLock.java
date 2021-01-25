package com.cyf.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈一锋
 * @date 2021/1/21 22:36
 **/
public class RepeatLock {
    public static void main(String[] args) throws InterruptedException {
        Resources resources = new Resources();
        // synchronized 是可重入锁
        new Thread(resources::sendMsg).start();
        new Thread(resources::sendMsg).start();
        new Thread(resources::sendMsg).start();
        new Thread(resources::sendMsg).start();

        Thread.sleep(3000);

        new Thread(resources).start();
        new Thread(resources).start();
        new Thread(resources).start();
        new Thread(resources).start();
    }
}

/**
 * 资源类 测试多线程环境
 */
class Resources implements  Runnable{

    private final Lock lock = new ReentrantLock();

    public synchronized void sendMsg(){
        System.out.println(Thread.currentThread().getName()+"\t send msg");
        sendEmail();
    }

    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t send email");
    }


    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t get lock");
            set();
        }finally {
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t set lock");
        }finally {
            lock.unlock();
        }
    }
}
