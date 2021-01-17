package com.cyf.nettybook.protocol.server;

import com.cyf.nettybook.protocol.codec.NettyMessageDecoder;
import com.cyf.nettybook.protocol.codec.NettyMessageEncoder;
import com.cyf.nettybook.protocol.constant.NettyConstant;
import com.cyf.nettybook.protocol.handler.HeartBeatResHandler;
import com.cyf.nettybook.protocol.handler.LoginAuthResHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author 陈一锋
 * @date 2021/1/17 12:38
 **/
public class NettyServer {
    public void bind() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            pipeline.addLast(new NettyMessageEncoder());
                            pipeline.addLast(new ReadTimeoutHandler(50));
                            pipeline.addLast(new LoginAuthResHandler());
                            pipeline.addLast(new HeartBeatResHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(NettyConstant.IP, NettyConstant.PORT).sync();
            System.out.println("server start success,port:" + NettyConstant.PORT);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().bind();
    }
}
