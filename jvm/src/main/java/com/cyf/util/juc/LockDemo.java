package com.cyf.util.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author 陈一锋
 * @date 2022/3/26 10:46 上午
 */
public class LockDemo {
    static final Consumer<Lock> consumer = lock -> {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取锁");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }
    };

    static final Consumer<Lock> consumerTime = lock -> {
        boolean r = false;
        try {
            r = lock.tryLock(20, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!r){
            System.out.println(Thread.currentThread().getName() + "尝试获取锁失败");
        }
        try {
            System.out.println(Thread.currentThread().getName() + "获取锁");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }
    };
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(()-> consumer.accept(lock)).start();
        new Thread(()-> consumer.accept(lock)).start();
        new Thread(()-> consumer.accept(lock)).start();
        new Thread(()-> consumerTime.accept(lock)).start();

    }
}
