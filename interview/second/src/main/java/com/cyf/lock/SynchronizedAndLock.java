package com.cyf.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈一锋
 * @date 2021/1/24 12:27
 **/
public class SynchronizedAndLock {
    public static void main(String[] args) {
        synchronized (new Object()){

        }
        ReentrantLock reentrantLock = new ReentrantLock();
    }
}
