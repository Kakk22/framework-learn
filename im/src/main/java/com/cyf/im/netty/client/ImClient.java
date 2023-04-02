package com.cyf.im.netty.client;

import com.cyf.im.netty.config.NettyProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 客户端实现
 *
 * @author 陈一锋
 * @date 2021/2/16 22:23
 **/
@Component
public class ImClient {

    @Resource
    private NettyProperties nettyProperties;

    private static final String HOST = "127.0.0.1";

    public ImClient() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS));
                            p.addLast(new ProtobufVarint32FrameDecoder());
                            //p.addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
                            p.addLast(new ProtobufVarint32LengthFieldPrepender());
                            p.addLast(new ProtobufEncoder());
                            p.addLast(new ImClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(HOST, nettyProperties.getPort()).sync();
            // 关闭连接
            f.channel().closeFuture().sync();
        } finally {
            //关闭线程组
            group.shutdownGracefully();
        }
    }
}
