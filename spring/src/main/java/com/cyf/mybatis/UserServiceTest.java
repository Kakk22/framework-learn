package com.cyf.mybatis;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring整合mybatis
 *
 * @author 陈一锋
 * @date 2021/2/22 21:41
 **/
public class UserServiceTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        UserMapper userMapper = (UserMapper) context.getBean("userMapper");
        System.out.println(userMapper.getUser(1));
    }
}
