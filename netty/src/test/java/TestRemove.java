import java.util.HashSet;
import java.util.Iterator;

/**
 * @author 陈一锋
 * @date 2021/1/4 21:21
 **/
public class TestRemove {


    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>(8);
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(6);
//        Iterator<Integer> iterator = set.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//            iterator.remove();
//        }

        for (Integer integer : set) {
            //会报错
            set.remove(integer);
        }
    }
}
