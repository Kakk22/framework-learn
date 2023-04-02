package com.cyf.nettybook.bio.timedemo.server;

import com.cyf.nettybook.bio.timedemo.handler.TimeServerHandler;
import com.cyf.nettybook.bio.timedemo.thread.TimeServerHandlerExecutePool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步I/O 使用线程池优化
 *
 * @author 陈一锋
 * @date 2021/1/4 14:13
 **/
public class ThreadTimeServer {

    public static void main(String[] args) throws IOException {
        int port = 9091;
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
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 10000);
            while (true) {
                socket = server.accept();
                //创建一个I/O任务线程池
                //由于线程池和消息队列是有界的。
                // 因此,无论客户端并发连接数多大，它都不会导致线程池过于膨胀或者内存溢出
                singleExecutor.execute(new TimeServerHandler(socket));
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
