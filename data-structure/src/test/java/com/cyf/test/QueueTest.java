package com.cyf.test;

import com.cyf.queue.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 陈一锋
 * @date 2022/9/17 4:31 下午
 */
public class QueueTest {

    @Test
    public void test_array() {
        Object[] objects = new Object[10];
        Assert.assertEquals(null, objects[0]);
    }


    @Test
    public void test_queue() {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.add(100);
        q.add(4);
        q.add(5);
        q.add(6);
        q.add(3);

        System.out.println("------------");

        while (true) {
            Integer poll = q.poll();
            if (poll == null){
                break;
            }
            System.out.println(poll);
        }
    }

    @Test
    public void test_simple() {
        System.out.println(100 >>> 1);
        System.out.println(200 >>> 1);
        System.out.println(201 >>> 1);
        System.out.println(202 >>> 1);

        System.out.println("---------");

        System.out.println(100 >> 1);
        System.out.println(200 >> 1);
        System.out.println(201 >> 1);
        System.out.println(202 >> 1);
    }
}
