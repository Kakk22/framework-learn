package com.cyf;

import com.cyf.mapper.UserMapper;
import com.cyf.plugin.Page;
import com.cyf.plugin.PageHelper;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 陈一锋
 * @date 2021/9/4 5:33 下午
 */
public class PagePluginTest {

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
    public void testTotal() {
        PageHelper.start(10, 1);
        userMapper.selectUserAndOrder();
    }
}
