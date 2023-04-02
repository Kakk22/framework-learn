package com.cyf.netty.advance.im.server;

import com.cyf.netty.advance.im.server.handler.DispatchHandler;
import com.cyf.netty.advance.im.server.handler.LoginRequestHandler;
import com.cyf.netty.advance.protocol.endec.MessageCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:13 下午
 */
public class ImServer {
    public static void main(String[] args) {
        final NioEventLoopGroup boss = new NioEventLoopGroup();
        final NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.group(boss, worker);
            DispatchHandler dispatchHandler = new DispatchHandler();
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
//                    p.addLast(new LoggingHandler());
                    p.addLast(new MessageCodec());
                    p.addLast(dispatchHandler);
                }
            });
            final ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
