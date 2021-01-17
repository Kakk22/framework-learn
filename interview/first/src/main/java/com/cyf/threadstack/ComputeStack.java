package com.cyf.threadstack;

/**
 * @author 陈一锋
 * @date 2021/1/17 19:35
 **/
public class ComputeStack {
    public static void main(String[] args) {
        int i = 1;
        i = i++;
        int j = i++;
        int k = i + ++i * i++;
        // i = 4
        System.out.println("i:" + i);
        // j = 1
        System.out.println("j:" + j);
        // k = 11
        System.out.println("k:" + k);
    }
}
