package com.cyf.netty.advance.im.server.session;

import io.netty.channel.Channel;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:23 下午
 */
public interface Session {

    void bind(Channel channel, String username);

    void unBind(Channel channel);

    Channel getByUserName(String username);
}
