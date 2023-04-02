package com.cyf.nettybook.netty.tcp.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * @author 陈一锋
 * @date 2021/1/6 14:43
 **/
public class TimeClient {

    public static void main(String[] args) throws Exception {
        int port = 9095;
        new TimeClient().connect(port, "127.0.0.1");
    }

    private void connect(int port, String host) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //这里添加解码器
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
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

        private byte[] req;

        private int counter;


        private TimeClientHandler() {
            req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf messages = null;
            for (int i = 0; i < 100; i++) {
                messages = Unpooled.buffer(req.length);
                messages.writeBytes(req);
                ctx.writeAndFlush(messages);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //注释的为发生 粘包拆包时的代码
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] req = new byte[buf.readableBytes()];
//            buf.readBytes(req);
//            String body = new String(req, UTF_8);
            String body = (String) msg;
            System.out.println("Now is:" + body + "; the counter is :" + ++counter);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            LOGGER.warning("Unexpected exception:" + cause.getMessage());
            ctx.close();
        }
    }
}
