package com.cyf.nettybook.protocol.handler;

import com.cyf.nettybook.protocol.message.Header;
import com.cyf.nettybook.protocol.message.MessageType;
import com.cyf.nettybook.protocol.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 陈一锋
 * @date 2021/1/16 16:22
 **/
public class LoginAuthResHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, Boolean> nodeCache = new ConcurrentHashMap<>();

    /**
     * 白名单 简单的使用 正式使用可以读取配置文件
     */
    private static final List<String> WHITE_LIST;

    static {
        WHITE_LIST = new ArrayList<>();
        WHITE_LIST.add("127.0.0.1");
        WHITE_LIST.add("47.107,53,172");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof NettyMessage) {
            NettyMessage message = (NettyMessage) msg;
            if (message.getHeader() != null &&
                    message.getHeader().getType() == MessageType.LOGIN_RES.getType()) {
                // 握手请求
                String nodeIndex = ctx.channel().remoteAddress().toString();
                if (nodeCache.containsKey(nodeIndex)) {
                    //重复登录
                    ctx.writeAndFlush(buildRes((byte) -1));
                } else {
                    //验证是否在白名单
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
                    String ip = inetSocketAddress.getAddress().getHostAddress();
                    if (WHITE_LIST.contains(ip)) {
                        // 回复成功消息
                        ctx.writeAndFlush(buildRes((byte) 0));
                        nodeCache.put(nodeIndex, true);
                    } else {
                        ctx.writeAndFlush(buildRes((byte) -1));
                    }
                }
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }

    private NettyMessage buildRes(byte result) {
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.getType());
        NettyMessage message = new NettyMessage();
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCache.clear();
        super.exceptionCaught(ctx, cause);
    }
}
