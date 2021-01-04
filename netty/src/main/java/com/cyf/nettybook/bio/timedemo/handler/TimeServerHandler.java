package com.cyf.nettybook.bio.timedemo.handler;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @author 陈一锋
 * @date 2021/1/4 14:46
 **/
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)) {
            String line;
            String currentTime;
            while ((line = in.readLine()) != null) {
                System.out.println("thread name:"+Thread.currentThread().getName()+"  The time server receive order:" + line);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(line) ? new Date(
                        System.currentTimeMillis()
                ).toString() : "BAD ORDER";
                out.println(currentTime);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            if (this.socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (this.socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
