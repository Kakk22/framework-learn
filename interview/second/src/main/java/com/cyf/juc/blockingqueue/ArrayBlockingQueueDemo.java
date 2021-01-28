package com.cyf.juc.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 数组结构堵塞队列
 *
 * @author 陈一锋
 * @date 2021/1/24 10:47
 **/
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        System.out.println("addAndRemove()----");
        //超出会报异常
        addAndRemove();
        System.out.println("offerAndPoll()----");
        //超出返回false
        offerAndPoll();
        System.out.println("pullAndTask()----");
        pullAndTask();
        System.out.println("timeoutOffer()----");
        timeoutOffer();
    }



    private static void addAndRemove() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //add 超过元素会抛出异常
        System.out.println(blockingQueue.add("1"));
        System.out.println(blockingQueue.add("2"));
        System.out.println(blockingQueue.add("3"));

        //抛出异常
        //System.out.println(blockingQueue.add("4"));

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // java.util.NoSuchElementException
        //System.out.println(blockingQueue.remove());
    }

    private static void offerAndPoll() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("1"));
        System.out.println(blockingQueue.offer("2"));
        System.out.println(blockingQueue.offer("3"));
        // 会返回false 不会报错
        System.out.println(blockingQueue.offer("4"));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 没有元素 返回null
        System.out.println(blockingQueue.poll());
    }

    private static void pullAndTask() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            blockingQueue.put("1");
            blockingQueue.put("1");
            blockingQueue.put("1");
            System.out.println("put了3个");
            // blockingQueue.put("1");

            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
            // 这里会阻塞
            //blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void timeoutOffer() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        try {
            blockingQueue.offer("1",2,TimeUnit.SECONDS);
            blockingQueue.offer("1",2,TimeUnit.SECONDS);
            blockingQueue.offer("1",2,TimeUnit.SECONDS);
            blockingQueue.offer("1",2,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
