package com.cyf.juc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池案例
 *
 * @author 陈一锋
 * @date 2021/1/25 22:37
 **/
public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        //固定线程数的线程池
        ExecutorService ThreadPool = Executors.newFixedThreadPool(5);
        //单个线程的线程池
        //ExecutorService ThreadPool = Executors.newSingleThreadExecutor();
        //带缓存的线程池
        //ExecutorService ThreadPool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                ThreadPool.execute(() -> System.out.println(Thread.currentThread().getName() + "处理业务"));
                TimeUnit.MICROSECONDS.sleep(200);
            }
        } finally {
            //关闭线程池
            ThreadPool.shutdown();
        }
    }
}
