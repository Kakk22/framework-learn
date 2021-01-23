package com.cyf.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore  semaphore.acquire()保证同一时间只有指定个数的线程执行
 *
 * @author 陈一锋
 * @date 2021/1/23 16:26
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        //模拟三个车位
        Semaphore semaphore = new Semaphore(3);
        //模拟6部车子
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
