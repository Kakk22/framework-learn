package com.cyf.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author 陈一锋
 * @date 2022/3/20 1:17 下午
 */
public class TimerTest {

    public static void main(String[] args) {
        // 内部维护任务队列 线程一直轮训任务队列
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello timer.....");
            }
        }, 20);
    }
}

class ScheduledExecutorServiceTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor poll = new ScheduledThreadPoolExecutor(2, Executors.defaultThreadFactory());
        poll.scheduleAtFixedRate(() -> System.out.println("开始执行任务了：" + System.currentTimeMillis()), 0, 5000, TimeUnit.MILLISECONDS);
        poll.scheduleWithFixedDelay(() -> System.out.println("开始执行任务了delay：" + System.currentTimeMillis()), 0, 1000, TimeUnit.MILLISECONDS);
    }
}