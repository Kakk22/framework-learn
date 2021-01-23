package com.cyf.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁案例
 * 写时独占
 * 读时共享
 *
 * @author 陈一锋
 * @date 2021/1/23 10:13
 **/
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        Cache cache = new Cache();

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.put(tempInt + "", tempInt);
            }).start();
        }
        for (int i = 0; i < 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.get(tempInt + "");
            }).start();
        }
    }

}

/**
 * 缓存类
 */
class Cache {

    private volatile Map<String, Object> cache = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写入" + key);
            Thread.sleep(1000);
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void get(String key) {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取");
            Object val = cache.get(key);
            System.out.println(Thread.currentThread().getName() + "读取完成:" + val);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void clear() {
        cache.clear();
    }
}
