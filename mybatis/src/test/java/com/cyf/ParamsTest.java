package com.cyf;

import com.cyf.mapper.UserMapper;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 陈一锋
 * @date 2021/8/8 10:55 下午
 */
public class ParamsTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    SqlSession sqlSession;
    UserMapper userMapper;

    @Before
    public void init() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(MybatisFirstCacheTest.class.getResourceAsStream("/mybatis.xml"));
        configuration = sqlSessionFactory.getConfiguration();
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void testOneParams(){
        userMapper.getOne(10);
    }

    @Test
    public void testTwoParams(){
        userMapper.selectByName(1,"11");
    }

    @Test
    public void testResultSet(){
        System.out.println(userMapper.getOne(1));
    }

}
