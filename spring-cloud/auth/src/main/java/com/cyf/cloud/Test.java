package com.cyf.cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈一锋
 * @date 2022/6/4 7:30 下午
 */
public class Test {

    public String maxVersion(String[] versions) {
        Map<Integer,List<Integer>> map = new HashMap<>();
        for (int i = 0; i < versions.length; i++) {
            map.put(i, new ArrayList<>());
        }
        for (int i = 0; i < versions.length; i++) {
            List<Integer> list = map.get(i);
            String version = versions[i];
            String[] num = version.split("\\.");
            for (int j = 0; j < num.length; j++) {
                list.add(Integer.valueOf(num[j]));
            }
        }
        return null;
    }


    public static void main(String[] args) {
        String[][] s = new String[][]{
                {"1.2.1", "2.9.1"}
        };

        Test test = new Test();
        for (int i = 0; i < s.length; i++) {
            System.out.println(test.maxVersion(s[i]));
        }
    }
}
