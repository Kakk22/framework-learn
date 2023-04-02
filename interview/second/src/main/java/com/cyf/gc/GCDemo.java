package com.cyf.gc;

/**  串行垃圾回收
 *   -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC
 *
 *   ParNew 新生代使用并行回收器 老年代使用Serial Old
 *   Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector with the Serial old collector is deprecated
 *   and will likely be removed in a future release
 *   java 8 后不再推荐如此搭配
 *   -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC
 *
 *   java8 默认垃圾回收器
 *   开启后 新生代及老年代开启并行线程进行垃圾回收
 *   -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
 *
 * @author 陈一锋
 * @date 2021/2/1 20:29
 **/
public class GCDemo {
    public static void main(String[] args) {
        while (true){
            byte[] bytes = new byte[5 * 1024 * 1024];
            System.out.println(bytes);
        }
    }
}
