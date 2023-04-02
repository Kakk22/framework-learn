package com.cyf.nettybook.protocol.netty.client;

import com.cyf.nettybook.protocol.netty.codec.NettyMessageDecoder;
import com.cyf.nettybook.protocol.netty.codec.NettyMessageEncoder;
import com.cyf.nettybook.protocol.netty.constant.NettyConstant;
import com.cyf.nettybook.protocol.netty.handler.HeartBeatReqHandler;
import com.cyf.nettybook.protocol.netty.handler.LoginAuthReqHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 客户端
 *
 * @author 陈一锋
 * @date 2021/1/17 12:19
 **/
public class NettyClient {
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private NioEventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port, String host) throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            pipeline.addLast(new NettyMessageEncoder());
                            //当30秒没有读到消息关闭连接
                            pipeline.addLast(new ReadTimeoutHandler(50));
                            pipeline.addLast(new LoginAuthReqHandler());
                            pipeline.addLast(new HeartBeatReqHandler());

                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(NettyConstant.IP, NettyConstant.PORT)).sync();
            System.out.println("connect success,port:" + port);
            future.channel().closeFuture().sync();
        } finally {
            //所有资源释放完毕后 清空资源 重新连接
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    //发起重新连接
                    connect(NettyConstant.PORT, NettyConstant.IP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient().connect(NettyConstant.PORT, NettyConstant.IP);
    }
}
