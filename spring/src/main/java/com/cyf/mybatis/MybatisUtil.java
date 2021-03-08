package com.cyf.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.Reader;

/**
 * @author 陈一锋
 * @date 2021/2/22 21:08
 **/
public final class MybatisUtil {
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
