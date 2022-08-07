package com.cyf.netty.advance.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 陈一锋
 * @date 2022/8/7 12:12 上午
 */
@Slf4j
public class HttpProtocolTest {

    public static void main(String[] args) {
        final NioEventLoopGroup boss = new NioEventLoopGroup();
        final NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.group(boss, worker);

            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    final ChannelPipeline p = ch.pipeline();
                    p.addLast(new LoggingHandler())
                            .addLast(new HttpServerCodec())
                            .addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                    log.debug("请求url:{}", msg.uri());

                                    final DefaultFullHttpResponse res = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                    byte[] content = "<h1>hello word</h1>".getBytes();
                                    res.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.length);
                                    res.content().writeBytes(content);
                                    //写回响应
                                    ctx.writeAndFlush(res);
                                }
                            });
                }
            });

            final ChannelFuture channelFuture = bootstrap.bind(8880).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
