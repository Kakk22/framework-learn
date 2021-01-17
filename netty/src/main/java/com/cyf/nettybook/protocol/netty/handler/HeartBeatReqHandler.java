package com.cyf.nettybook.protocol.netty.handler;

import com.cyf.nettybook.protocol.netty.message.Header;
import com.cyf.nettybook.protocol.netty.message.MessageType;
import com.cyf.nettybook.protocol.netty.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

/**
 * 心跳请求处理
 *
 * @author 陈一锋
 * @date 2021/1/17 11:56
 **/
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof NettyMessage) {
            NettyMessage message = (NettyMessage) msg;
            if (message.getHeader() != null &&
                    message.getHeader().getType() == MessageType.LOGIN_RESP.getType()) {
                //登录验证通过发送心跳消息
                heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
            } else if (message.getHeader() != null &&
                    message.getHeader().getType() == MessageType.HEART_RESP.getType()) {
                // 收到心跳回复消息
                System.out.println("client receive server heart msg" + message);
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage message = buildHeartMsg();
            System.out.println("client send heart messages to server:" + message);
            ctx.writeAndFlush(message);
        }
    }

    private NettyMessage buildHeartMsg() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEART_RES.getType());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        super.exceptionCaught(ctx, cause);
    }
}
