package com.cyf.netty.advance.im.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:23 下午
 */
public class DefaultSessionImpl implements Session {

    public static final Map<String, Channel> NAME_CHANNEL_MAP = new ConcurrentHashMap<>(256);
    public static final Map<Channel, String> CHANNEL_NAME_MAP = new ConcurrentHashMap<>(256);

    @Override
    public void bind(Channel channel, String username) {
        NAME_CHANNEL_MAP.putIfAbsent(username, channel);
        CHANNEL_NAME_MAP.putIfAbsent(channel, username);
    }

    @Override
    public void unBind(Channel channel) {
        String username = CHANNEL_NAME_MAP.remove(channel);
        NAME_CHANNEL_MAP.remove(username);
    }

    @Override
    public Channel getByUserName(String username) {
        return NAME_CHANNEL_MAP.get(username);
    }
}
