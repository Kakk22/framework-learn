package com.cyf.classinit;

/**
 * 父类初始化<clinit>:
 * 1、j = method();
 * 2、父类的静态代码块
 * 静态变量和静态代码块有顺序之分,谁在前面先加载谁
 * <p>
 * 父类的实例化方法<init></>:
 * (1) super()最前
 * (2) i = test()
 * (3) 父类的非静态代码块
 * (4) 父类的无参构造器(最后)
 * ---------------------------------
 * 非静态方法前面其实有一个默认的对象this
 * this在构造器(<init></>) 它表示正在创建的对象
 * test()执行的时子类重写的代码(面向对象多态)
 *
 * @author 陈一锋
 * @date 2021/1/17 22:42
 **/
public class Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(1)");
    }

    public Father() {
        System.out.print("(2)");
    }

    {
        System.out.print("(3)");
    }


    public int test() {
        System.out.print("(4)");
        return 1;
    }

    public static int method() {
        System.out.print("(5)");
        return 1;
    }
}
