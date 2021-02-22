package com.cyf.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/2/20 21:00
 **/
public class SpringJDBCTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-jdbc.xml");

        UserService userService = (UserService) applicationContext.getBean("userService");

        User user = new User();
        user.setName("张三");
        user.setAge(10);
        user.setSex("男");

        userService.save(user);

        List<User> users = userService.getUsers();
        for (User u : users) {
            System.out.println("get all users");
            System.out.println(u);
        }
    }
}
