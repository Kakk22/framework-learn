package com.cyf;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈一锋
 * @date 2021/1/25 12:19
 **/
public class FinallyDemo {


    public static void main(String[] args) {
        System.out.println(test());
    }

    private static int test() {
        int i = 1;
        try {
            return i;
        } finally {
            i = 2;
            //这里会覆盖掉try里面的return
            //如果没有return 则i不会改变值
            return i;
        }
    }


}
