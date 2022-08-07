package com.cyf.netty.advance.im.server.service;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:31 下午
 */
public interface UserService {

    boolean login(String username, String password);
}
