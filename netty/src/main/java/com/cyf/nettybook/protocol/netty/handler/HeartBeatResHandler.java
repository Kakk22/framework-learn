package com.cyf.nettybook.protocol.netty.handler;

import com.cyf.nettybook.protocol.netty.message.Header;
import com.cyf.nettybook.protocol.netty.message.MessageType;
import com.cyf.nettybook.protocol.netty.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 心跳回复请求
 *
 * @author 陈一锋
 * @date 2021/1/17 12:07
 **/
public class HeartBeatResHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof NettyMessage) {
            NettyMessage message = (NettyMessage) msg;
            if (message.getHeader() != null
                    && message.getHeader().getType() == MessageType.HEART_RES.getType()) {
                //返回心跳消息
                System.out.println("server receive client heart msg" + message);
                NettyMessage heartMsg = buildHeartMsg();
                System.out.println("server send msg to client:" + message);
                ctx.writeAndFlush(heartMsg);
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }

    private NettyMessage buildHeartMsg() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEART_RESP.getType());
        message.setHeader(header);
        return message;
    }
}
