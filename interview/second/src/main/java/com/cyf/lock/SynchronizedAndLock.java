package com.cyf.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈一锋
 * @date 2021/1/24 12:27
 **/
public class SynchronizedAndLock {
    public static void main(String[] args) {
        synchronized (new Object()) {

        }
        ReentrantLock reentrantLock = new ReentrantLock();
    }

    //反编译
    /**
     *   public static void main(java.lang.String[]);
     *     descriptor: ([Ljava/lang/String;)V
     *     flags: ACC_PUBLIC, ACC_STATIC
     *     Code:
     *       stack=2, locals=3, args_size=1
     *          0: new           #2                  // class java/lang/Object
     *          3: dup
     *          4: invokespecial #1                  // Method java/lang/Object."<init>":()V
     *          7: dup
     *          8: astore_1
     *          9: monitorenter   加对象锁
     *         10: aload_1
     *         11: monitorexit    退出锁
     *         12: goto          20
     *         15: astore_2
     *         16: aload_1
     *         17: monitorexit    退出锁 防止不释放锁造成死锁
     *         18: aload_2
     *         19: athrow
     *         20: new           #3                  // class java/util/concurrent/locks/ReentrantLock
     *         23: dup
     *         24: invokespecial #4                  // Method java/util/concurrent/locks/ReentrantLock."<init>":()V
     *         27: astore_1
     *         28: return
     */
}
