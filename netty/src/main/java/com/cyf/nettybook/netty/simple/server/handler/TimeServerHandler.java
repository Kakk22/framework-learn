package com.cyf.nettybook.netty.simple.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 陈一锋
 * @date 2021/1/6 14:17
 **/
public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, UTF_8);
        System.out.println("The time server receive order:" + body);
        String currTime = "QUERY THE ORDER".equalsIgnoreCase(body) ?
                new Date(System.currentTimeMillis()).toString() :
                "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currTime.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
