package com.cyf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/1/11 23:13
 **/
public class HeadTest {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
    }
}
