package com.cyf.netty.advance.im.server.handler;

import com.cyf.netty.advance.im.server.message.Message;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author 陈一锋
 * @date 2022/8/7 2:36 下午
 */
public interface MessageHandler<T extends Message> {

    void handle(T msg, ChannelHandlerContext ctx);
}
