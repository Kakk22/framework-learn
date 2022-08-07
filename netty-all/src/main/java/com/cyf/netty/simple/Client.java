package com.cyf.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author 陈一锋
 * @date 2022/8/5 10:59 下午
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new StringEncoder());
                    }
                })
                //连接服务器
                .connect(new InetSocketAddress("localhost", 8899))
                //堵塞等待连接成功
                .sync()
                //获取channel
                .channel()
                //写入数据并发送
                .writeAndFlush("hello netty");

    }
}
