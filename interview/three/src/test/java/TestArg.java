/**
 * @author 陈一锋
 * @date 2021/2/23 18:46
 **/
public class TestArg {

    String str = "hello";
    char[] ch = {'a', 'b'};

    public static void main(String[] args) {
        TestArg testArg = new TestArg();
        testArg.change(testArg.str, testArg.ch);
        System.out.print(testArg.str + "and" + testArg.ch);
    }

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'c';
    }
}
