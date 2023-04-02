package com.cyf.juc.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 循环屏障  当线程累加到指定值时 调用其线程任务
 * @author 陈一锋
 * @date 2021/1/23 16:33
 **/
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () ->
                System.out.println("召唤神龙")
        );

        for (int i = 0; i < 7; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    System.out.println("收集到第" + temp + "龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
