package com.cyf.limit.only;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单限流
 *
 * @author 陈一锋
 * @date 2022/4/12 9:32 下午
 */
public class RateLimiterSimpleWindow {

    private static final Integer qps = 2;
    /**
     * 时间窗口
     */
    private static long window = 1000;
    private static long startTime = System.currentTimeMillis();
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static boolean tryAcquire() {
        synchronized (RateLimiterSimpleWindow.class) {
            if ((System.currentTimeMillis() - startTime) > window) {
                startTime = System.currentTimeMillis();
                atomicInteger.set(0);
            }
        }
        return atomicInteger.incrementAndGet() <= qps;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(400);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(250);
            LocalTime now = LocalTime.now();
            if (!tryAcquire()) {
                System.out.println(now + "被限流");
            } else {
                System.out.println(now + "正常");
            }
        }
    }
}
