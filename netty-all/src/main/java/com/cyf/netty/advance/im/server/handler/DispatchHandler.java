package com.cyf.netty.advance.im.server.handler;

import com.cyf.netty.advance.im.server.handler.factory.HandlerFactory;
import com.cyf.netty.advance.im.server.message.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author 陈一锋
 * @date 2022/8/7 2:35 下午
 */
@ChannelHandler.Sharable
public class DispatchHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        HandlerFactory.getHandler(msg.messageType()).handle(msg, ctx);
    }
}
