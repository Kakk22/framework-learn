package com.cyf;

import com.cyf.mapper.UserMapper;
import com.cyf.model.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/8/7 12:35 上午
 */
public class MybatisFirstCacheTest {

    SqlSessionFactory sqlSessionFactory;
    SqlSession sqlSession;

    @Before
    public void init() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(MybatisFirstCacheTest.class.getResourceAsStream("/mybatis.xml"));
        sqlSession = sqlSessionFactory.openSession();
    }

    /**
     * 一级缓存生效的条件:
     * 1.sql 相同,参数相同
     * 2.statementId 相同及同一类同一方法
     * 3.sqlSession 必须相同(会话级别缓存)
     * 4.分页必须相同
     */
    @Test
    public void t1() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //-----走一级缓存
        User one = mapper.getOne(1);
        User one1 = mapper.getOne(1);
        System.out.println("是否走一级缓存:" + (one == one1));
        //不走一级缓存 statement id 不同
        //statementId com.cyf.mapper.UserMapper.getOne
        User one2 = mapper.getOne(1);
        // com.cyf.mapper.UserMapper.getOne1
        User one3 = mapper.getOne1(1);
        System.out.println("是否走一级缓" +
                "存:" + (one2 == one3));

        //与User one2 = mapper.getOne(1); 一样。底层都是调用这个方法
        User user = sqlSession.selectOne("com.cyf.mapper.UserMapper.getOne", 1);

        //与上一个一样 可以命中一级缓存 因为分页参数一样
        List<User> list = sqlSession.selectList("com.cyf.mapper.UserMapper.getOne", 1, RowBounds.DEFAULT);
    }

    /**
     * 1.未清空缓存
     * 2.未执行update语句 (update会先清空掉一级缓存再执行)
     * 3.缓存作用域不是statement
     * 4.未调用flushCache = true操作
     */
    @Test
    public void t2() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getOne(1);
        sqlSession.clearCache();
        User user1 = mapper.getOne(1);
        System.out.println(user == user1);
    }
}
