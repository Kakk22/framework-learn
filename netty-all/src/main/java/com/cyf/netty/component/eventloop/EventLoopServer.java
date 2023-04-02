package com.cyf.netty.component.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author 陈一锋
 * @date 2022/8/6 11:24 上午
 */
@Slf4j
public class EventLoopServer {

    public static void main(String[] args) {
        //处理业务的线程池
        final DefaultEventLoop dfGroup = new DefaultEventLoop();
        new ServerBootstrap()
                // boss group只负责NioServerSocketChannel上的accept事件     worker group 负责SocketChannel上的io事件
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast("handler1", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        final ByteBuf bf = (ByteBuf) msg;
                                        log.info("接收到消息:{}", bf.toString(Charset.defaultCharset()));
                                        ctx.fireChannelRead(msg);
                                    }
                                })
                                .addLast(dfGroup, "handler2", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        final ByteBuf bf = (ByteBuf) msg;
                                        log.info("接收到消息:{}", bf.toString(Charset.defaultCharset()));
                                    }
                                });
                    }
                })
                .bind(8080);
    }
}
