package com.cyf.netty.advance.im.server.handler;

import com.cyf.netty.advance.im.server.message.LoginRequestMessage;
import com.cyf.netty.advance.im.server.message.LoginResponseMessage;
import com.cyf.netty.advance.im.server.message.Message;
import com.cyf.netty.advance.im.server.service.UserServiceFactory;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author 陈一锋
 * @date 2022/8/7 1:22 下午
 */

public class LoginRequestHandler extends AbstractMessageHandler implements MessageHandler<LoginRequestMessage> {

    @Override
    public void handle(LoginRequestMessage msg, ChannelHandlerContext ctx) {
        //接受到登录消息
        boolean login = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
        //登录成功
        if (login) {
            this.getSession().bind(ctx.channel(), msg.getUsername());
            ctx.writeAndFlush(new LoginResponseMessage(true, "登录成功"));
        } else {
            //登录失败
            ctx.writeAndFlush(new LoginResponseMessage(false, "登录失败"));
        }
    }
}
