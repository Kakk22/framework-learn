import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/1/16 16:48
 **/
public class TestStatic {
    public static void main(String[] args) {
        A a = new A();
        A a1 = new A();
        System.out.println(A.list == A.list);

    }


}

class A {
    public static List<Integer> list;

    static {
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
    }
}