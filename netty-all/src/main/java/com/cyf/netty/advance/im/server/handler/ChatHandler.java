package com.cyf.netty.advance.im.server.handler;

import com.cyf.netty.advance.im.server.message.ChatRequestMessage;
import com.cyf.netty.advance.im.server.message.ChatResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author 陈一锋
 * @date 2022/8/7 1:46 下午
 */
public class ChatHandler extends AbstractMessageHandler implements MessageHandler<ChatRequestMessage> {

    @Override
    public void handle(ChatRequestMessage msg, ChannelHandlerContext ctx) {
        Channel toChannel = this.getSession().getByUserName(msg.getTo());
        if (toChannel == null) {
            System.out.println("接收人不存在或者已经下线:" + msg.getTo());
            ctx.writeAndFlush(new ChatResponseMessage(false, "接收人不存在或者已经下线:"));
        } else {
            toChannel.writeAndFlush(new ChatResponseMessage(true, msg.getContent()));
        }
    }
}
