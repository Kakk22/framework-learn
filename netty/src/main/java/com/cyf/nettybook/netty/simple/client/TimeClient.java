package com.cyf.nettybook.netty.simple.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * @author 陈一锋
 * @date 2021/1/6 14:43
 **/
public class TimeClient {

    public static void main(String[] args) throws Exception {
        int port = 9094;
        new TimeClient().connect(port, "127.0.0.1");
    }

    private void connect(final int port, final String host) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    private class TimeClientHandler extends ChannelInboundHandlerAdapter {

        public final Logger LOGGER = Logger.getLogger(TimeClientHandler.class.getName());

        private final ByteBuf firstMessages;

        private TimeClientHandler() {
            byte[] req = "QUERY THE ORDER".getBytes();
            firstMessages = Unpooled.buffer(req.length);
            firstMessages.writeBytes(req);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(firstMessages);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, UTF_8);
            System.out.println("Now is:" + body);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            LOGGER.warning("Unexpected exception:" + cause.getMessage());
            ctx.close();
        }
    }


}
