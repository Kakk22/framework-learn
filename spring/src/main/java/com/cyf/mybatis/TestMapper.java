package com.cyf.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

/**
 * @author 陈一锋
 * @date 2021/2/22 21:13
 **/
public class TestMapper {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        sqlSessionFactory = MybatisUtil.getSessionFactory();
    }


    @Test
    public void testAdd(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User tom = new User("tom", 5);
            mapper.insertUser(tom);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testGet(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUser(2);
            System.out.println(user);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

}
