package com.cyf.mybatis.mapper;

import com.cyf.mybatis.User;

/**
 * @author 陈一锋
 * @date 2021/2/22 20:52
 **/
public interface UserMapper {
    /**
     * 新增
     * @param user /
     */
    void insertUser(User user);

    /**
     * 获取
     * @param id /
     * @return /
     */
    User getUser(Integer id);

}
