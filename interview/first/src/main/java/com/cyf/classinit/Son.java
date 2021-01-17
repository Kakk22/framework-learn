package com.cyf.classinit;

/**
 * 先初始化父类: (5)(1)
 * 初始化子类: (10) (6)
 * --------------------
 * 子类的实例化方法<init></>:
 * (1) super()最前  (9)(3)(2)
 * (2) i = test()   (9) 由于子类重写了该方法
 * (3) 子类的非静态代码块 (8)
 * (4) 子类的无参构造器(最后) (7)
 *
 * @author 陈一锋
 * @date 2021/1/17 22:45
 **/
public class Son extends Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.print("(6)");
    }

    public Son() {
        System.out.print("(7)");
    }

    {
        System.out.print("(8)");
    }

    @Override
    public int test() {
        System.out.print("(9)");
        return 1;
    }

    public static int method() {
        System.out.print("(10)");
        return 2;
    }

    public static void main(String[] args) {
        Son son1 = new Son();
        System.out.println();
        Son son2 = new Son();
    }
}
