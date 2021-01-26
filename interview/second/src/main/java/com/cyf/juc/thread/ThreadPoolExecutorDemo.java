package com.cyf.juc.thread;

import java.util.concurrent.*;

/**
 * 定义线程池
 *
 * @author 陈一锋
 * @date 2021/1/20 21:00
 **/
public class ThreadPoolExecutorDemo {


    public static void main(String[] args) {
        // 超过堵塞队列及最大线程数 报异常
        abortPolicy();
        // 回退到执行线程 执行任务
        callerRunsPolicy();
        // 抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再起提交当前任务
        discardOldestPolicy();
        // 直接丢弃任务。如果运行任务丢失,这是最好的方案
        discardPolicy();

    }

    private static void abortPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "\t 处理业务"));
        }
    }

    private static void callerRunsPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "\t 处理业务"));
        }
    }

    private static void discardOldestPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "\t 处理业务"));
        }
    }

    private static void discardPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "\t 处理业务"));
        }
    }

}
