package com.cyf.nettybook.nio.server;

/**
 * NIO 实现客户端
 *
 * @author 陈一锋
 * @date 2021/1/4 20:48
 **/
public class NIOTimeServer {
    public static void main(String[] args) {
        int port = 9092;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //使用默认值
            }
        }
        //多路复用类
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}
