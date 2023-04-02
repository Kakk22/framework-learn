package com.cyf.im.netty.server;


import com.cyf.im.netty.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * im服务端
 *
 * @author 陈一锋
 * @date 2021/2/5 15:26
 **/


@Component
@Slf4j
public class ImServer {

    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final NioEventLoopGroup workGroup = new NioEventLoopGroup();

    @Resource
    private NettyProperties properties;

    @PostConstruct
    public void init() {

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ProtobufVarint32FrameDecoder());
                        p.addLast(new ProtobufVarint32LengthFieldPrepender());
                        // 超过30秒未发送消息则发送心跳包
                        p.addLast(new IdleStateHandler(60, 30, 0));
                        p.addLast(new ImServerHandler());
                    }
                });

        try {
            ChannelFuture f = b.bind(properties.getPort()).sync();

            //添加监听器
            f.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("Netty server start successful,port:{}", properties.getPort());
                } else {
                    log.error("Netty server start error:{}", future.cause());
                    throw new RuntimeException("Netty server start fail !", future.cause());
                }
            });

            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Netty server start error:{}", e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            log.info("destroy netty server complete.");
        }

    }
}
