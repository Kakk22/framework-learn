package com.cyf.netty.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 陈一锋
 * @date 2022/8/14 2:17 下午
 */
@Slf4j
public class RpcServer {

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup work;
    private Integer port = 8888;

    public RpcServer() {
        this.serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup(3);
        serverBootstrap.group(boss, work);
    }

    public void start() {
        serverBootstrap
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringDecoder());
                    }
                });
        ChannelFuture future = serverBootstrap.bind(port);
        future.addListener(f -> {
            if (f.isSuccess()) {
                log.info("rpc server start success!");
            } else {
                log.error("rpc server start error:", f.cause());
                future.channel().close().sync();
            }
        });
    }
}
