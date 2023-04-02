package com.cyf.nio.bock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author 陈一锋
 * @date 2022/8/3 9:16 下午
 */
public class Client {
    public static void main(String[] args) throws IOException {
        final SocketChannel sc = SocketChannel.open();
        final boolean c = sc.connect(new InetSocketAddress("localhost", 9090));
        System.out.println("连接:" + c);
        sc.write(StandardCharsets.UTF_8.encode("hello"));
    }
}
