package com.cyf.netty.advance.im.server.handler;


import com.cyf.netty.advance.im.server.session.Session;
import com.cyf.netty.advance.im.server.session.SessionFactory;

/**
 * @author 陈一锋
 * @date 2022/8/7 2:44 下午
 */
public abstract class AbstractMessageHandler {

    protected Session getSession() {
        return SessionFactory.getSession();
    }
}
