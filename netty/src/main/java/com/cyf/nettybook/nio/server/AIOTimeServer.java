package com.cyf.nettybook.nio.server;

import com.cyf.nettybook.nio.server.handler.AsynTimeServerHandler;

/**
 * @author 陈一锋
 * @date 2021/1/5 18:19
 **/
public class AIOTimeServer {

    public static void main(String[] args) {
        int port = 9093;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //使用默认值
            }
        }
        AsynTimeServerHandler timeServerHandler = new AsynTimeServerHandler(port);
        new Thread(timeServerHandler, "AIO-AsyncTimeServerHandler-001").start();
    }
}
