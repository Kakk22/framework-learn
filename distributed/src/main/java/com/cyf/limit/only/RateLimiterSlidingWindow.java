package com.cyf.limit.only;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 滑动窗口限流
 *
 * @author 陈一锋
 * @date 2022/5/22 10:14 下午
 */
public class RateLimiterSlidingWindow {
    /**
     * 阈值
     */
    private int qps;
    /**
     * 多少个子窗口
     */
    private int windowCount = 10;
    /**
     * 时间窗口总大小 ms
     */
    private Integer windowSize = 1000;
    /**
     * 窗口列表
     */
    private WindowInfo[] windowInfos = new WindowInfo[windowCount];

    public RateLimiterSlidingWindow(int qps) {
        this.qps = qps;
        long currentTimes = System.currentTimeMillis();
        for (int i = 0; i < windowInfos.length; i++) {
            windowInfos[i] = new WindowInfo(currentTimes, new AtomicInteger(0));
        }
    }

    /**
     * 1.计算当前时间窗口
     * 2.更新当前窗口计数,重置过期窗口计数
     * 3.当前QPS是否超过限制
     */
    public synchronized boolean tryAcquire() {
        long currentTimeMillis = System.currentTimeMillis();
        //计算当前时间窗口
        int currentIndex = (int) (currentTimeMillis % windowSize / (windowSize / windowCount));
        //2.更新当前窗口计数.重置过期窗口计数
        int sum = 0;
        for (int i = 0; i < windowInfos.length; i++) {
            WindowInfo windowInfo = windowInfos[i];
            if ((currentTimeMillis - windowInfo.getTimes()) > windowSize) {
                windowInfo.getNumber().set(0);
                windowInfo.setTimes(currentTimeMillis);
            }
            if (currentIndex == i && windowInfo.getNumber().get() < qps) {
                windowInfo.getNumber().incrementAndGet();
            }
            sum = sum + windowInfo.getNumber().get();
        }
        // 3.当前qps是否超过限制
        return sum <= qps;
    }

    @Data
    @AllArgsConstructor
    private class WindowInfo {
        private Long times;
        private AtomicInteger number;
    }


    @SneakyThrows
    public static void main(String[] args) {
        int qps = 2, count = 20, sleep = 300, success = count * sleep / 1000 * qps;
        System.out.println(String.format("当前QPS限制为:%d,当前测试次数:%d,间隔:%dms,预计成功次数:%d", qps, count, sleep, success));
        success = 0;
        RateLimiterSlidingWindow myRateLimiter = new RateLimiterSlidingWindow(qps);
        for (int i = 0; i < count; i++) {
            Thread.sleep(sleep);
            if (myRateLimiter.tryAcquire()) {
                success++;
                if (success % qps == 0) {
                    System.out.println(LocalTime.now() + ": success, ");
                } else {
                    System.out.print(LocalTime.now() + ": success, ");
                }
            } else {
                System.out.println(LocalTime.now() + ": fail");
            }
        }
        System.out.println();
        System.out.println("实际测试成功次数:" + success);
    }
}
