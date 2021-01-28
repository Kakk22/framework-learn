package com.cyf.juc.array;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈一锋
 * @date 2021/1/20 20:48
 **/
public class ContainerNotSafe {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        // ArrayList 线程不安全 会出现java.util.ConcurrentModificationException
        List<String> list = new ArrayList<>();
        //线程安全
        //1.Collections.synchronizedList  其实内部就是在所有方法加synchronized
        //2.Vector
        //3.CopyOnWriteArrayList 写时复制列表
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
//        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }).start();
        }
    }
}
