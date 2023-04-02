package com.cyf.nettybook.protocol.netty.handler;

import com.cyf.nettybook.protocol.netty.message.Header;
import com.cyf.nettybook.protocol.netty.message.MessageType;
import com.cyf.nettybook.protocol.netty.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 握手请求处理器
 *
 * @author 陈一锋
 * @date 2021/1/15 21:02
 **/
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyMessage message = buildReq();
        System.out.println("建立连接,发送握手请求数据:" + message.toString());
        ctx.writeAndFlush(message);
    }

    /**
     * 如果是握手应答消息
     * 判断是否认证成功
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
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
