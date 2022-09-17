package com.cyf.company.jimi;

import com.sun.java.swing.action.OkAction;

import javax.swing.tree.TreeNode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 合并有序两个数组 并取中位数
 * [1,3,4] [2,5,6] -> [1,2,3,4,5,6]   (3+4)/2 = 3.5
 *
 * @author 陈一锋
 * @date 2022/9/3 10:32 上午
 */
public class MergeList {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        int i = new MergeList().mergeList(new int[]{1, 3, 4}, new int[]{2, 5, 6});
//        int i1 = new MergeList().mergeList(new int[]{1, 3}, new int[]{2, 5, 6});
//        int i2 = new MergeList().mergeList(new int[]{1, 3, 4, 7, 8}, new int[]{2});

        List<Integer> list = new ArrayList<>();
        list.add(100);

        Method method = list.getClass().getMethod("add", Object.class);
        method.invoke(list,"hello");

        System.out.println(list);

        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    public int mergeList(int[] sum1, int[] sum2) {
        int total = sum1.length + sum2.length;
        int k = 0, j = 0;
        int[] res = new int[total];
        for (int i = 0; i < total; i++) {
            if ((sum1[k] < sum2[j])) {
                res[i] = sum1[k];
                if (k + 1 < sum1.length) {
                    k++;
                }
            } else {
                res[i] = sum2[j];
                //设置为失效
                sum2[j] = -1;
                if (j + 1 < sum2.length) {
                    j++;
                }
            }
        }
        System.out.println(Arrays.toString(res));
        return 0;
    }
}
