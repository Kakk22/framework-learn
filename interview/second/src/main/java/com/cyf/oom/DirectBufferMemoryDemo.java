package com.cyf.oom;

import java.nio.ByteBuffer;

/**
 * JVM 参数
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 *
 *
 * @author 陈一锋
 * @date 2021/1/31 22:32
 **/
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        //Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
        //可以直接使用Native直接分配内存 如果本地内存不足 则报错Direct buffer memory
        ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
