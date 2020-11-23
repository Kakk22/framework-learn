package com.cyf;

import com.cyf.service.MessageService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author by cyf
 * @date 2020/11/2.
 */
public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        System.out.println("context 启动成功");
        MessageService messageService = context.getBean(MessageService.class);
        System.out.println(messageService.getMessages());
    }
}
