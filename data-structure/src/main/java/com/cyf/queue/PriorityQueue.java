package com.cyf.queue;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 基于最小堆实现
 * 最小堆：本质是一个完全二叉树,其父节点一定比叶子节点小
 *
 * @author 陈一锋
 * @date 2022/9/17 4:18 下午
 */
@Slf4j
public class PriorityQueue<E> extends AbstractQueue<E> {

    private int size = 0;

    private Object[] queue;

    private final static int DEFAULT_SIZE = 10;

    public PriorityQueue() {
        queue = new Object[DEFAULT_SIZE];
    }

    @Override
    public boolean offer(E o) {
        if (o == null)
            throw new NullPointerException();
        int s = size;
        if (s >= queue.length) {
            grow();
        }
        size = s + 1;
        if (s == 0) {
            queue[0] = o;
        } else {
            //堆调整
            siftUpComparable(s, o);
        }
        return true;
    }

    /**
     * @param k 当前调整元素的索引位置
     * @param e 元素
     */
    @SuppressWarnings("unchecked")
    private void siftUpComparable(int k, E e) {
        Comparable<? super E> key = (Comparable<? super E>) e;
        while (k > 0) {
            //获取父节点的索引 // k = (k-1) >>> 1;等同
            int parent = (k - 1) / 2;
            Object p = queue[parent];
            if (key.compareTo((E) p) > 0) {
                log.info("当前节点比父节点大,退出循环:idx:{},key:{},parent:{}", parent, key, p);
                break;
            }
            //当前节点比父节点小 交换位置
            queue[k] = p;
            k = parent;
        }
        queue[k] = e;
        log.info("调整堆结束,当前key:{},队列:{}", e, queue);
    }

    private void grow() {
        int oldCapacity = queue.length;
        // Double size if small; else grow by 50%
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                (oldCapacity + 2) :
                (oldCapacity >> 1));

        queue = Arrays.copyOf(queue, newCapacity);
    }


    @SuppressWarnings("unchecked")
    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        int s = --size;
        E res = (E) queue[0];
        E e = (E) queue[s];
        queue[s] = null;
        siftDownComparable(0, e);
        return res;
    }

    @SuppressWarnings("unchecked")
    private void siftDownComparable(int k, E e) {
        Comparable<? super E> key = (Comparable<? super E>) e;
        //找到中间节点
        int half = size >>> 1;
        while (k < half) {
            //找到左子节点和右子节点 比较大小
            int left = (k << 1) + 1;
            int right = left + 1;
            Object c = queue[left];
            if (right < size && ((Comparable<? super E>) c).compareTo((E) queue[right]) >= 0) {
                //交换对象
                left = right;
                c = queue[left];
            }

            //此时c是两个子节点中较小的
            if (key.compareTo((E) c) <= 0) {
                //此时key已经是合适的位置了
                break;
            }

            //当前节点设置为较小的节点
            queue[k] = c;
            //将指针指向较小节点
            k = left;
        }

        queue[k] = key;

    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        return (E) queue[0];
    }


    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}
