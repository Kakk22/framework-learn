package com.cyf;

import com.cyf.service.MessageService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author by cyf
 * @date 2020/11/2.
 */
public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
       // XmlBeanFactory context = new XmlBeanFactory(new ClassPathResource("application.xml"));
        System.out.println("context 启动成功");        MessageService messageService = context.getBean(MessageService.class);
        System.out.println(messageService.getMessages());

        ClassPathResource resource = new ClassPathResource("application.xml");
        try (InputStream in = resource.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
