package com.cyf;

import com.cyf.mapper.UserMapper;
import com.cyf.vo.UserVo;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/8/22 10:00 下午
 */
public class ResultSetTest {

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
    public void t1(){
        List<UserVo> userVos = userMapper.selectUserAndOrder();
        System.out.println(userVos);
    }
}
