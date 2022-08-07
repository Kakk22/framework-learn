package com.cyf.netty.advance.im.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:31 下午
 */
public class DefaultUserService implements UserService {

    public static final Map<String, String> USER_MAP = new ConcurrentHashMap<>();

    static {
        USER_MAP.put("zhangsan", "123");
        USER_MAP.put("lisi", "123");
        USER_MAP.put("wanwu", "123");
        USER_MAP.put("cyf", "123");
    }


    @Override
    public boolean login(String username, String password) {
        String userPassword = USER_MAP.get(username);
        if (userPassword != null && userPassword.equals(password)) {
            return true;
        }
        return false;
    }
}
