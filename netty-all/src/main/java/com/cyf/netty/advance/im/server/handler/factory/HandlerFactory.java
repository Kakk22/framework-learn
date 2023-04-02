package com.cyf.netty.advance.im.server.handler.factory;

import com.cyf.netty.advance.im.server.handler.ChatHandler;
import com.cyf.netty.advance.im.server.handler.LoginRequestHandler;
import com.cyf.netty.advance.im.server.handler.MessageHandler;
import com.cyf.netty.advance.im.server.message.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈一锋
 * @date 2022/8/7 2:35 下午
 */
public class HandlerFactory {

    public static final Map<Integer, MessageHandler> HANDLER_MAP = new HashMap<>();

    static {
        HANDLER_MAP.put(Message.LOGIN_REQUEST_CODE, new LoginRequestHandler());
        HANDLER_MAP.put(Message.CHAT_REQUEST_CODE, new ChatHandler());
        HANDLER_MAP.put(Message.CHAT_RESPONSE_CODE, new ChatHandler());
    }

    public static MessageHandler getHandler(Integer code) {
        return HANDLER_MAP.get(code);
    }
}
