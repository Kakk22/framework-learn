package com.cyf.netty.component.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author 陈一锋
 * @date 2022/8/6 11:26 上午
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        final ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        sc.pipeline().addLast(new StringEncoder());
                    }
                })
                //连接服务器 这个方法是异步非堵塞的
                .connect(new InetSocketAddress("localhost", 8080));

        //堵塞等待连接成功
        channelFuture.sync();
        //获取channel
        final Channel c = channelFuture.channel();
//        //写入数据并发送
//        c.writeAndFlush("hello netty");
//        System.out.println(c);
//        System.out.println("--");

//        //注册建立成功监听器
//        channelFuture.addListener((ChannelFutureListener) future -> {
//            //获取channel
//            final Channel c = future.channel();
//            //写入数据并发送
//            c.writeAndFlush("hello netty");
//            System.out.println(c);
//            System.out.println("--");
//        });

        new Thread(()->{
            final Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                final String data = scanner.next();
                if ("q".equals(data)) {
                    //关闭不是同步关闭 而是异步的
                    c.close();
                    break;
                }
                //写入数据并发送
                c.writeAndFlush(data);
            }
        },"input").start();

        final ChannelFuture closeFuture = channelFuture.channel().closeFuture();
        //同步等待关闭
        closeFuture.sync();
        System.out.println("关闭了连接");

//        //或者可以
//        closeFuture.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                log.debug("处理关闭后的事情..");
//            }
//        });

    }
}
