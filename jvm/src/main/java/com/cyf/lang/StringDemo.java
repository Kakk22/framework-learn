package com.cyf.lang;

/**
 * @author 陈一锋
 * @date 2022/3/13 11:39 上午
 */
public class StringDemo {
    public static void main(String[] args) {
        /**
         * 1.final类不可继承，安全
         * 2.java提供+语法是通过StringBuilder或StringBuffer实现的
         * 3.内部有一个char类型的数组提供可变字符串
         * 4.主要方法用:subString indexOf,startWith,endWith,equal,equalsIgnoreCase,toLowerCase,toUpperCase,spilt,concat(连接字符串)
         */
        String a = "123";
        String b = "456" + a;
        System.out.println(a.equalsIgnoreCase("1"));
        System.out.println(a.toLowerCase());
        System.out.println(a.toUpperCase());
        System.out.println(a.startsWith("1"));
        System.out.println(b);

        /**
         * StringBuilder StringBuffer
         * 1.都是继承AbstractStringBuilder
         * 2.StringBuilder线程不安全,StringBuffer线程安全 内部方法都是用synchronized修饰
         * 3.AbstractStringBuilder内部维护char类型数组,StringBuilder和StringBuffer构造器默认数组大小为16
         */

        StringBuilder builder = new StringBuilder();
        String res = builder.append("1")
                .append("2")
                .append("3")
                .toString();
        System.out.println(res);
    }
}
