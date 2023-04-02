package com.cyf.nettybook.filesystem;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author 陈一锋
 * @date 2021/1/11 21:49
 **/
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/main/com/cyf/nettybook";
    private static final int PORT = 8081;

    public static void main(String[] args) throws InterruptedException {
        new HttpFileServer().run();
    }

    public void run() throws InterruptedException {



        EventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-decoder", new HttpRequestDecoder());
                            //请求消息解码器 将多个消息转化为单一的FullHttpRequest 或 FullHttpResponse
                            pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
                            pipeline.addLast("http-encoder", new HttpResponseEncoder());
                            //这个是支持异步发送大的码流
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            pipeline.addLast("fileServerHandler", new HttpFileServerHandler(DEFAULT_URL));
                        }
                    });
            ChannelFuture future = bootstrap.bind("10.200.10.134", PORT).sync();
            System.out.println("HTTP 文件目录系统启动成功,网站是:10.200.10.134:" + PORT + DEFAULT_URL);
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    private class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
        private final String url;

        public HttpFileServerHandler(String url) {
            this.url = url;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        }
    }
}
