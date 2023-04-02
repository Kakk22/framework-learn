package com.cyf.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author: 陈一锋
 * @date: 2021/8/2 10:46 下午
 */
public class MybatisUtil {
    private final static SqlSessionFactory SESSION_FACTORY;

    private MybatisUtil() {
    }

    static {
        String resource = "mybatis.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SESSION_FACTORY = new SqlSessionFactoryBuilder().build(reader);
    }

    public static SqlSessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
