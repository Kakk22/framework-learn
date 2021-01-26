package com.cyf.juc.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author 陈一锋
 * @date 2021/1/23 10:46
 **/
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "离开教室");
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "全部都离开了，main关灯走人");


    }
}
