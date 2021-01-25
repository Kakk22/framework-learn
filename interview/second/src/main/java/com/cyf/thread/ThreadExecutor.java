package com.cyf.thread;

import java.util.concurrent.*;

/**
 * 定义线程池
 *
 * @author 陈一锋
 * @date 2021/1/20 21:00
 **/
public class ThreadExecutor {

    private static final BlockingQueue<Runnable> LINKED_BLOCKING_DEQUE = new LinkedBlockingDeque<>(
            1024);

    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 10, 30,
            TimeUnit.SECONDS, LINKED_BLOCKING_DEQUE,
            new ThreadPoolExecutor.AbortPolicy());

    public static void execute(Runnable r) {
        executorService.execute(r);
    }

}
