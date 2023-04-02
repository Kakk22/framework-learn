package com.cyf.netty.rpc;

import com.alibaba.fastjson.JSON;
import com.cyf.netty.rpc.proxy.Request;
import com.cyf.netty.rpc.proxy.Response;
import com.cyf.netty.rpc.register.ServerRegister;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 陈一锋
 * @date 2022/8/14 2:17 下午
 */
@Slf4j
public class RpcServer implements Lifecycle {

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup work;
    private Integer port = 8888;
    private Channel channel;

    private ServerRegister serverRegister;

    public RpcServer(ServerRegister serverRegister) {
        this.serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup(3);
        serverBootstrap.group(boss, work);
        this.serverRegister = serverRegister;
    }

    @Override
    public void start() {
        serverBootstrap
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
//                                .addLast(new LoggingHandler(LogLevel.DEBUG))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        Request request = JSON.parseObject(((String) msg), Request.class);
                                        System.out.println("接收到消息:" + request);

                                        String className = request.getClassName();
                                        Object server = serverRegister.getServer(className);
                                        Method method = Class.forName(className).getMethod(request.getMethod(), request.getArgs()[0].getClass());
                                        Object res = method.invoke(server, request.getArgs());

                                        Response response = new Response();
                                        response.setUid(request.getRequestId());
                                        response.setData(res);
                                        System.out.println("返回接口:" + response);
                                        ctx.writeAndFlush(JSON.toJSONString(response));
                                    }
                                });
                    }
                });
        ChannelFuture future = serverBootstrap.bind(port);
        future.addListener(f -> {
            if (f.isSuccess()) {
                log.info("rpc server start success!");
                channel = future.channel();
            } else {
                log.error("rpc server start error:", f.cause());
                future.channel().close().sync();
            }
        });
    }

    @Override
    public void stop() {
        if (channel != null) {
            channel.close();
        }
    }
}
