package com.cyf.async;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author 陈一锋
 * @date 2022/4/3 8:49 下午
 */
public class MarkWordTest {
    public static void main(String[] args) throws InterruptedException {
        //对象信息
//        com.cyf.async.Dog object internals:
//        OFF  SZ   TYPE DESCRIPTION               VALUE
//        0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
//        8   4        (object header: class)    0xf800c044
//        12   4        (object alignment gap)
//        Instance size: 16 bytes
//        Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        Thread.sleep(4000);
        Dog dog = new Dog();
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        synchronized (dog){
            //加完锁后 对象头有存入线程id
//            com.cyf.async.Dog object  internals:
//            OFF  SZ   TYPE DESCRIPTION               VALUE
//            0   8        (object header: mark)     0x00007f9fdc013805 (biased: 0x0000001fe7f7004e; epoch: 0; age: 0)
//            8   4        (object header: class)    0xf800c044
//            12   4        (object alignment gap)
//            Instance size: 16 bytes
//            Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(dog).toPrintable());
    }
}

class Dog{

}
