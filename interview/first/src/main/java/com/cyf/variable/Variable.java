package com.cyf.variable;

/**
 * @author 陈一锋
 * @date 2021/1/18 20:06
 **/
public class Variable {
    static int s;
    int i;
    int j;

    {
        int i = 1;
        i++;//这里的i指上一行的i 如果用this 则为成员变量的i
        j++;
        s++;
    }

    public void test(int j) {
        j++;//这里的j为行参j
        i++;
        s++;
    }

    public static void main(String[] args) {
        //初始化两个实例 由于s是静态变量  则无论哪个实例每次执行test方法s都会自增
        Variable obj1 = new Variable();
        Variable obj2 = new Variable();
        obj1.test(10);
        obj1.test(20);
        obj2.test(30);
        System.out.println(obj1.i + "," + obj1.j + "," + obj1.s);
        System.out.println(obj2.i + "," + obj2.j + "," + obj2.s);
    }
}
