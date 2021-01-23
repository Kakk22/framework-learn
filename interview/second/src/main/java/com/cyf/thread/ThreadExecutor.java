package com.cyf.thread;

import java.util.concurrent.*;

/**
 * 定义线程池
 *
 * @author 陈一锋
 * @date 2021/1/20 21:00
 **/
public class ThreadExecutor {

    private static BlockingQueue<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(
            1024);

    private static ExecutorService executorService = new ThreadPoolExecutor(1, 10, 30,
            TimeUnit.SECONDS, linkedBlockingDeque,
            new ThreadPoolExecutor.AbortPolicy());

    public static void execute(Runnable r) {
        executorService.execute(r);
    }

}
