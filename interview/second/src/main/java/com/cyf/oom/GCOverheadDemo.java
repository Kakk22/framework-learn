package com.cyf.oom;

import java.util.ArrayList;
import java.util.List;

/**JVM 参数
 *  -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 *  GC回收时间过长会抛出OutOfMemoryError  过长的定义,超过98%的时间来做GC并且回收了不到2%的堆内存
 *
 * @author 陈一锋
 * @date 2021/1/31 22:21
 **/
public class GCOverheadDemo {

    //java.lang.OutOfMemoryError: GC overhead limit exceeded
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();

        try {
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        }catch (Throwable e){
            System.out.println("*************i="+i);
            e.printStackTrace();
            throw e;
        }
    }
}
