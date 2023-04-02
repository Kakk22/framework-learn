package com.cyf.util.list;

import java.util.LinkedList;

/**
 * @author 陈一锋
 * @date 2022/4/10 1:16 下午
 */
public class LinkListDemo {
    public static void main(String[] args) {
        //双向链表 内部元素都封装成node节点,node节点内部维护前后指针
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(list);
    }
}
