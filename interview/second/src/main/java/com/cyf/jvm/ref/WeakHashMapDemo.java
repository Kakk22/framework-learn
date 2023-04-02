package com.cyf.jvm.ref;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author 陈一锋
 * @date 2021/1/28 21:19
 **/
public class WeakHashMapDemo {

    public static void main(String[] args) {
        //hashmap为强引用 key为null node节点也不会被回收
        //myHashMap();
        // weakHashMap 为弱引用 gc会回收其node节点 如果key为null
        myWeakHashMap();
    }

    private static void myWeakHashMap() {
        Map<Integer, String> map = new WeakHashMap<>(2);
        Integer i = new Integer(1);
        String s = "weakHashmap";

        map.put(i,s);

        System.out.println(map);

        i = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }

    private static void myHashMap() {
        Map<Integer, String> map = new HashMap<>(2);
        Integer i = new Integer(1);
        String s = "hashmap";

        map.put(i,s);

        System.out.println(map);

        i = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }
}
