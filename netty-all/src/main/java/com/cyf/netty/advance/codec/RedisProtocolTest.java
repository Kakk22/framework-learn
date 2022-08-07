package com.cyf.netty.advance.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author 陈一锋
 * @date 2022/8/6 11:53 下午
 */
@Slf4j
public class RedisProtocolTest {
    public static void main(String[] args) {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        /**
         * redis协议
         * set name cyf222
         * *3
         * $3
         * set
         * $4
         * name
         * $6
         * cyf222
         */
        try {
            byte[] line = {13, 10};
            final Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(
                                    new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            //连接建立后回触发 active事件
                                            final ByteBuf bf = ctx.alloc().buffer();
                                            bf.writeBytes("*3".getBytes());
                                            bf.writeBytes(line);
                                            bf.writeBytes("$3".getBytes());
                                            bf.writeBytes(line);
                                            bf.writeBytes("set".getBytes());
                                            bf.writeBytes(line);
                                            bf.writeBytes("$4".getBytes());
                                            bf.writeBytes(line);
                                            bf.writeBytes("name".getBytes());
                                            bf.writeBytes(line);
                                            bf.writeBytes("$6".getBytes());
                                            bf.writeBytes(line);
                                            bf.writeBytes("cyf222".getBytes());
                                            bf.writeBytes(line);
                                            ctx.writeAndFlush(bf);
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            final ByteBuf bf = (ByteBuf) msg;
                                            log.debug("接受redis的响应:{}", bf.toString(Charset.defaultCharset()));
                                        }
                                    }
                            );
                        }
                    })
                    .connect("localhost", 6379)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
