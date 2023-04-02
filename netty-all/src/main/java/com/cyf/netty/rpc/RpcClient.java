package com.cyf.netty.rpc;

import com.alibaba.fastjson.JSON;
import com.cyf.netty.rpc.client.ResponseFuture;
import com.cyf.netty.rpc.proxy.Request;
import com.cyf.netty.rpc.proxy.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈一锋
 * @date 2022/8/14 2:17 下午
 */
public class RpcClient implements Lifecycle {

    private final Bootstrap bootstrap;
    private final NioEventLoopGroup group;

    private Map<InetSocketAddress, Channel> channelMap = new ConcurrentHashMap<>();

    private final static Map<Long, ResponseFuture> responseTable = new ConcurrentHashMap<>(256);

    private final Lock lock = new ReentrantLock();

    public RpcClient() {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
    }

    @Override
    public void start() {
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
//                                .addLast(new LoggingHandler(LogLevel.DEBUG))
                                .addLast(new StringEncoder())
                                .addLast(new StringDecoder())
                                .addLast(new SimpleChannelInboundHandler<String>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                        System.out.println("接收到服务器返回结果:" + s);
                                        Response response = JSON.parseObject(s, Response.class);
                                        ResponseFuture responseFuture = responseTable.get(response.getUid());
                                        if (responseFuture != null){
                                            responseFuture.putResponse(response.getData());
                                        }
                                    }
                                });
                    }
                });
    }

    public Object sendSync(InetSocketAddress addr, Request data, int timeoutMilliseconds) {
        Channel channel = this.getChannel(addr);
        ResponseFuture responseFuture = new ResponseFuture();
        data.setRequestId(responseFuture.getUid());
        this.responseTable.put(responseFuture.getUid(), responseFuture);
        channel.writeAndFlush(JSON.toJSONString(data));
        responseFuture.await(timeoutMilliseconds);

        return responseFuture.getData();

    }

    private Channel getChannel(InetSocketAddress addr) {
        Channel channel = channelMap.get(addr);
        if (channel == null) {
            lock.lock();
            try {
                channel = channelMap.get(addr);
                if (channel == null) {
                    ChannelFuture connect = bootstrap.connect(addr);
                    try {
                        channel = connect.sync().channel();
                        channelMap.putIfAbsent(addr, channel);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
        }
        return channel;
    }


    @Override
    public void stop() {
        channelMap.values().forEach(ChannelOutboundInvoker::close);
    }
}
