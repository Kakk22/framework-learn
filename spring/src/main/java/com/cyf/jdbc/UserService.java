package com.cyf.jdbc;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/2/20 20:52
 **/
public interface UserService {

    /**
     * 新增
     * @param user /
     */
    void save(User user);

    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<User> getUsers();
}
