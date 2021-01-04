package com.cyf.nettybook.bio.timedemo.server;

import com.cyf.nettybook.bio.timedemo.handler.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 陈一锋
 * @date 2021/1/4 14:13
 **/
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 9090;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //使用默认值
            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("the time server is start in port:" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                //一个线程只能处理一个客户端连接
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }


    }
}
