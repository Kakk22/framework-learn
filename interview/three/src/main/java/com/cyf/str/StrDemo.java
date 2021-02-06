package com.cyf.str;

/**
 * @author 陈一锋
 * @date 2021/2/2 20:49
 **/
public class StrDemo {
    public static void main(String[] args) {
        String str1 = new StringBuilder().append("ali").append("baba").toString();
        System.out.println(str1);
        System.out.println(str1.intern());
        // true
        System.out.println(str1 == str1.intern());

        System.out.println();

        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        // false 因为java 在加载已经加载过进常量池了
        System.out.println(str2 == str2.intern());
    }
}
