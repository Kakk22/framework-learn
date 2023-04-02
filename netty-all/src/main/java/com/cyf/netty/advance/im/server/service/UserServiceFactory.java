package com.cyf.netty.advance.im.server.service;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:31 下午
 */
public class UserServiceFactory {

    public static final UserService USER_SERVICE = new DefaultUserService();

    public static UserService getUserService() {
        return USER_SERVICE;
    }
}
