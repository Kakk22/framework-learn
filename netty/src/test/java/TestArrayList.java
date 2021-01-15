import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 陈一锋
 * @date 2021/1/15 14:28
 **/
public class TestArrayList {
//    private static List<Object> list = new ArrayList<>();
    private static List<Object> list = new CopyOnWriteArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            for (int i = 0; i <10000 ; i++) {
                list.add(i);
            }
            System.out.println(list.size());
        };
        new Thread(r).start();
        new Thread(r).start();
        new Thread(r).start();
    }
}
