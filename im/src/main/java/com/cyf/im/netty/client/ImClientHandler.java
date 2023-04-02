package com.cyf.im.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author 陈一锋
 * @date 2021/2/16 22:39
 **/
public class ImClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive msg" + msg.toString());
    }


}
