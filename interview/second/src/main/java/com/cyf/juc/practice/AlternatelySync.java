package com.cyf.juc.practice;

/**
 * @author 陈一锋
 * @date 2021/1/24 19:06
 **/
public class AlternatelySync {


    public static void main(String[] args) {
        Task task = new Task();
        new Thread(task, "A").start();
        new Thread(task, "B").start();

    }
}


class Task implements Runnable {
    int count = 0;
    private final Object obj = new Object();

    @Override
    public void run() {
        while (count <= 100) {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getName() + "print" + count);
                count++;
                obj.notifyAll();
                try {
                    if (count <= 100) {
                        obj.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}