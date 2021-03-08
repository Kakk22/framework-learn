package com.cyf.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 陈一锋
 * @date 2021/2/2 14:55
 **/
public class TestAop {
    private String str = "testAop";


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void test() {
        System.out.println(this.str);
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        TestAop test = (TestAop) context.getBean("testAop");
        test.test();
    }
}
