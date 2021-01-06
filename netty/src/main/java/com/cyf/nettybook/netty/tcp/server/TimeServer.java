package com.cyf.nettybook.netty.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;

/**
 * 基于netty的时间服务器
 *
 * @author 陈一锋
 * @date 2021/1/6 14:09
 **/
public class TimeServer {

    public static void main(String[] args) throws Exception {
        int port = 9095;
        new TimeServer().bind(port);
    }

    private void bind(int port) throws Exception {
        //配置服务器NIO线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            //绑定端口
            ChannelFuture f = b.bind(port).sync();
            //等待服务器监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            //添加这两个解码器 LineBasedFrameDecoder  StringDecoder
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

    /**
     * 消息处理器
     */
    class TimeServerHandler extends ChannelInboundHandlerAdapter {

        private int count;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //这种情况会发送粘包 拆包
//            ByteBuf byteBuf = (ByteBuf) msg;
//            byte[] req = new byte[byteBuf.readableBytes()];
//            byteBuf.readBytes(req);
//            String body = new String(req, UTF_8).substring(0, req.length - System.getProperty("line.separator").length());
            //使用编码器后接收的为msg就是删除回车换行符后的请求消息
            String body = (String) msg;
            System.out.println("The time server receive order:" + body + ";the counter is :" + ++count);

            String currTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                    new Date(System.currentTimeMillis()).toString() :
                    "BAD ORDER";
            currTime = currTime + System.getProperty("line.separator");
            ByteBuf resp = Unpooled.copiedBuffer(currTime.getBytes());
            ctx.writeAndFlush(resp);
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }


}
