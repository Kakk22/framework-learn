package com.cyf.nettybook.nio.client;

/**
 * @author 陈一锋
 * @date 2021/1/5 14:11
 **/
public class NIOTimeClient {

    public static void main(String[] args) {
        int port = 9092;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //使用默认值
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
