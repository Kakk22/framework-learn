package com.cyf.nio.bock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author 陈一锋
 * @date 2022/8/3 9:10 下午
 */
public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();

        ssc.bind(new InetSocketAddress(9090));

        final ByteBuffer buffer = ByteBuffer.allocate(20);
        while (true){
            //堵塞等待tcp连接,三次握手后从全联接队列获取
            final SocketChannel sc = ssc.accept();
            //堵塞读
            sc.read(buffer);
            buffer.flip();
            System.out.println(StandardCharsets.UTF_8.decode(buffer));
            buffer.clear();
        }
    }
}
