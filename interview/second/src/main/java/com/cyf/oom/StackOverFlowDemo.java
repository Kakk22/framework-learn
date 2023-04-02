package com.cyf.oom;

/**
 * 栈溢出错误
 *
 * @author 陈一锋
 * @date 2021/1/31 22:07
 **/
public class StackOverFlowDemo {

    public static void main(String[] args) {
        //Exception in thread "main" java.lang.StackOverflowError
        overflow();
    }

    private static void overflow() {
        overflow();
    }
}
