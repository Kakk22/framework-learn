package com.cyf.nettybook.bio.timedemo.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author 陈一锋
 * @date 2021/1/4 17:36
 **/
public class TimeServerHandlerExecutePool {

    private ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("demo-pool-").build();
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize), threadFactory);
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }
}
