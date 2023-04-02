package com.cyf.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 陈一锋
 * @date 2021/1/25 21:27
 **/
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask,"A").start();

        System.out.println(futureTask.get());
    }
}

class MyThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 1024;
    }
}