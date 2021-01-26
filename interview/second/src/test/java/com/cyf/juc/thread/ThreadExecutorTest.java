package com.cyf.juc.thread;

/**
 * @author 陈一锋
 * @date 2021/1/21 11:52
 **/
class ThreadExecutorTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 1; ; ) {
            i++;
            if (i == 2 << 30) {
                System.out.println("end,time is " + (System.currentTimeMillis() - start) + "ms");
                break;
            }
        }
    }
}