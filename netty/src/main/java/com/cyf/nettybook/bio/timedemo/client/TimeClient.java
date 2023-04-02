package com.cyf.nettybook.bio.timedemo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author 陈一锋
 * @date 2021/1/4 15:00
 **/
public class TimeClient {
    public static void main(String[] args) throws IOException {
//        int port = 9090;
        int port = 9091;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //使用默认值
            }
        }
        Socket socket = new Socket("127.0.0.1", port);
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed");
            String resp = in.readLine();
            System.out.println("Now is:" + resp);
        } catch (Exception e) {
            //不需要处理
        } finally {
            socket.close();
        }
    }
}
