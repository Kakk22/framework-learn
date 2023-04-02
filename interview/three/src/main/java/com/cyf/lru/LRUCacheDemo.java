package com.cyf.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * 手写一个lur
 * 实现其实就是一个 map+双向链表
 *
 * @author 陈一锋
 * @date 2021/2/10 21:49
 **/
@SuppressWarnings("all")
public class LRUCacheDemo {

    private int cacheSize;
    private Map<Integer, Node<Integer, Integer>> map;
    private DoubleLinkedList<Integer, Integer> doubleLinkedList;

    public LRUCacheDemo(int cacheSize) {
        this.cacheSize = cacheSize;
        map = new HashMap<>(16);
        doubleLinkedList = new DoubleLinkedList<>();
    }

    public void put(int key, int val) {
        //1.update or save
        //2.if save and the node size bigger than cacheSize,remove the last node
        if (map.containsKey(key)) {
            Node<Integer, Integer> node = map.get(key);
            node.val = val;
            map.put(key, node);

            //重新将元素加到队列头部
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        } else {
            //超过大小 使用lru删除元素
            if (map.size() == cacheSize) {
                Node<Integer, Integer> delNode = doubleLinkedList.getLast();
                map.remove(delNode.key);
                doubleLinkedList.removeNode(delNode);
            }
            //新增
            Node<Integer, Integer> node = new Node<>(key, val);
            doubleLinkedList.addHead(node);
            map.put(key, node);
        }
    }

    public int get(int key) {
        //不存在
        if (!map.containsKey(key)) {
            return -1;
        }
        Node<Integer, Integer> node = map.get(key);
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);

        return node.val;
    }


    /**
     * 构建一个链表
     */
    static class DoubleLinkedList<K, V> {
        private Node<K, V> head;
        private Node<K, V> tail;

        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            this.head.next = tail;
            this.tail.pre = head;
        }

        /**
         * 添加一个node到链表头
         */
        public void addHead(Node<K, V> node) {
            node.next = head.next;
            node.pre = head;
            head.next.pre = node;
            head.next = node;
        }

        /**
         * 获取最后一个节点
         */
        public Node<K, V> getLast() {
            return tail.pre;
        }

        /**
         * 删除一个节点
         */
        public void removeNode(Node<K, V> node) {
            node.next.pre = node.pre;
            node.pre.next = node.next;
            node.next = null;
            node.pre = null;
        }
    }

    /**
     * 构建一个数据载体
     */
    static class Node<K, V> {
        K key;
        V val;
        Node<K, V> next;
        Node<K, V> pre;

        public Node() {
            this.pre = this.next = null;
        }

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.next = null;
            this.pre = null;
        }
    }

    public static void main(String[] args) {
        LRUCacheDemo lru = new LRUCacheDemo(3);

        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        System.out.println(lru.map.keySet());
        lru.put(4, 4);
        System.out.println(lru.map.keySet());
        lru.put(5, 3);
        lru.put(6, 3);
        lru.put(7, 3);
        System.out.println(lru.get(3));
        System.out.println(lru.map.keySet());


    }
}
