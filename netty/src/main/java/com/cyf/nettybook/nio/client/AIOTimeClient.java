package com.cyf.nettybook.nio.client;

import java.nio.channels.CompletionHandler;

/**
 * @author 陈一锋
 * @date 2021/1/5 18:52
 **/
public class AIOTimeClient {
    public static void main(String[] args) {
        int port = 9093;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //使用默认值
            }
        }
        new Thread(new AsynTimeClientHandler("127.0.0.1", port), "AsynTimeClient-001").start();
    }
}
