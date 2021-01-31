package com.cyf.oom;

/**
 * 堆溢出案例
 * 申请的内存比堆还大 垃圾回收后也没有内存
 * 抛出 java.lang.OutOfMemoryError: Java heap space
 * @author 陈一锋
 * @date 2021/1/31 22:15
 **/
public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        while (true){
            //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            byte[] bytes = new byte[80 * 1024 * 1024];
        }
    }
}
