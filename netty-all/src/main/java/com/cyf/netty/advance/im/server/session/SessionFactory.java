package com.cyf.netty.advance.im.server.session;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:23 下午
 */
public abstract class SessionFactory {

    public static final Session SESSION = new DefaultSessionImpl();

    public static Session getSession() {
        return SESSION;
    }
}
