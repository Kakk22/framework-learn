package com.cyf.mybatis;

import com.cyf.batch.BatchUtil;
import com.cyf.mybatis.mapper.UserMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * spring整合mybatis
 *
 * @author 陈一锋
 * @date 2021/2/22 21:41
 **/
public class UserServiceTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
//        UserMapper userMapper = (UserMapper) context.getBean("userMapper");
//        UserMapper userMapper1 = (UserMapper) context.getBean("userMapper");
//        System.out.println(userMapper.getUser(1));

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 102; i++) {
            User user = new User();
            user.setName("userbatch"+i);
            users.add(user);
        }
        BatchUtil.batch(users, UserMapper.class, UserMapper::insertUser);
    }
}
