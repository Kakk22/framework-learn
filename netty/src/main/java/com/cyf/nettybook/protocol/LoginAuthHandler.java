package com.cyf.nettybook.protocol;

import com.cyf.nettybook.protocol.message.Header;
import com.cyf.nettybook.protocol.message.MessageType;
import com.cyf.nettybook.protocol.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author 陈一锋
 * @date 2021/1/15 21:02
 **/
public class LoginAuthHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildReq());
    }

    /**
     * 如果是握手应答消息
     * 判断是否认真成功
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_RESP.getType()) {
            byte body = (byte) message.getBody();
            if (body != 0) {
                //握手失败,关闭连接
                ctx.close();
            } else {
                System.out.println("login is success:" + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RES.getType());
        message.setHeader(header);
        return message;
    }
}
