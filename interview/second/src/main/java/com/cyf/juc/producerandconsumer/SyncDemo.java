package com.cyf.juc.producerandconsumer;

/**
 * 生产者消费者模型
 * 生产一个 消费一个
 * @author 陈一锋
 * @date 2021/2/3 21:00
 **/
public class SyncDemo {
    public static void main(String[] args) {
        Resource resource = new Resource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t get result" + resource.get());
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.put();
            }
        }).start();
    }
}


class Resource {
    private int i;
    private boolean available = false;

    public int getI() {
        return i;
    }


    private final Object obj = new Object();

    public void put() {
        synchronized (obj) {
            while (available) {
                // 为true则等待生产
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //生产
            i++;
            System.out.println(Thread.currentThread().getName() + "\t put" + i);
            available = true;
            obj.notifyAll();
        }
    }

    public int get() {
        synchronized (obj) {
            while (!available) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            available = false;
            obj.notifyAll();
            return i;
        }
    }
}