package com.cyf.producerandconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 堵塞队列实现生产者消费者模型
 *
 * @author 陈一锋
 * @date 2021/1/24 20:17
 **/
public class BlockingQueueDemo {


    public static void main(String[] args) {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(5);

        Pro pro = new Pro(blockingQueue);
        new Thread(() -> pro.produce(), "A").start();
        new Thread(() -> pro.consume(), "B").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();




        }
        pro.stop();
    }
}

class Pro {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private final BlockingQueue<String> blockingQueue;

    Pro(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void produce() {
        while (flag) {
            String data = atomicInteger.incrementAndGet() + "";
            boolean result = blockingQueue.offer(data);
            if (result) {
                System.out.println(Thread.currentThread().getName() + "插入数据" + data + "成功");
            }else {
                System.out.println(Thread.currentThread().getName() + "插入数据" + data + "失败");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t 收到信息停止生产");
    }

    public void consume() {
        while (flag) {
            try {
                String result = blockingQueue.poll(2, TimeUnit.SECONDS);
                if (result == null || "".equals(result)) {
                    flag = false;
                    System.out.println(Thread.currentThread().getName() + "\t 超过两秒没有收到信息,退出消费");
                }else {
                    System.out.println(Thread.currentThread().getName() + "\t 开始消费");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        this.flag = false;
    }

}
