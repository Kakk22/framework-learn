package com.cyf.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 陈一锋
 * @date 2022/3/15 9:02 下午
 */
public class ListDemo {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(1);
        list.add(1);
        list.add(2);
        System.out.println(Arrays.toString(list.toArray()));
    }
}
