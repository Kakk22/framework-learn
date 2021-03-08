package com.cyf.gc;

/**
 * @author 陈一锋
 * @date 2021/2/1 19:51
 **/
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello gc");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
