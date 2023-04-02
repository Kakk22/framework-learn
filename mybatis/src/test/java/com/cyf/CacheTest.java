package com.cyf;

import com.cyf.mapper.UserMapper;
import com.cyf.model.User;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 陈一锋
 * @date 2021/8/7 7:23 下午
 */
public class CacheTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    SqlSession sqlSession;

    @Before
    public void init() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(MybatisFirstCacheTest.class.getResourceAsStream("/mybatis.xml"));
        configuration = sqlSessionFactory.getConfiguration();
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void t1(){
        Cache cache = configuration.getCache("com.cyf.mapper.UserMapper");

        //二级缓存流程
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User one = mapper.getOne(1);
        sqlSession.commit();

        User one1 = mapper.getOne(1);
        System.out.println(one1 == one);
    }
}
