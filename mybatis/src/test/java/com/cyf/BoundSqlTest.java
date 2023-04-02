package com.cyf;

import com.cyf.model.User;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈一锋
 * @date 2021/8/29 11:17 上午
 */
public class BoundSqlTest {
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
    public void testIf(){
        User user = new User();
        user.setId(1);

        DynamicContext dynamicContext = new DynamicContext(configuration, user);
        //静态节点
        new StaticTextSqlNode("select * from user").apply(dynamicContext);

        //if节点
        IfSqlNode ifSqlNode = new IfSqlNode(new StaticTextSqlNode("and id = #{id}"), "id!=null");
//        ifSqlNode.apply(dynamicContext);

        //添加where前缀 移除关键字的前后缀
        WhereSqlNode whereSqlNode = new WhereSqlNode(configuration, ifSqlNode);
        whereSqlNode.apply(dynamicContext);

        System.out.println(dynamicContext.getSql());
    }


    @Test
    public void testMix(){
        User user = new User();
        user.setId(1);
        user.setName("1");

        DynamicContext dynamicContext = new DynamicContext(configuration, user);
        //静态节点
        new StaticTextSqlNode("select * from user").apply(dynamicContext);
        //if节点
        IfSqlNode ifSqlNode = new IfSqlNode(new StaticTextSqlNode("and id = #{id} "), "id!=null");
        IfSqlNode ifSqlNode2 = new IfSqlNode(new StaticTextSqlNode("and name = #{name}" ), "name!=null");
        //有多个节点 遍历
        MixedSqlNode mixedSqlNode = new MixedSqlNode(Arrays.asList(ifSqlNode, ifSqlNode2));

        //添加where前缀 移除关键字的前后缀
        WhereSqlNode whereSqlNode = new WhereSqlNode(configuration, mixedSqlNode);
        whereSqlNode.apply(dynamicContext);

        System.out.println(dynamicContext.getSql());
    }

    @Test
    public void foreachTest(){
        Map<Object,Object> map = new HashMap<>();
        map.put("list",Arrays.asList("1","2","3","4","5"));
        List<Object> selectById = sqlSession.selectList("selectById", map);
        System.out.println(selectById);
    }

    @Test
    public void testStaticSqlNode(){
        //静态sql脚本解析
        List<Object> selectById = sqlSession.selectList("selectUserAndOrder", null);
        System.out.println(selectById);
    }

    @Test
    public void testDynamicSqlNode(){
        //静态sql脚本解析
        Map<Object,Object> map = new HashMap<>();
        map.put("list",Arrays.asList("1","2","3","4","5"));
        List<Object> selectById = sqlSession.selectList("selectById", map);
        List<Object> selectById1 = sqlSession.selectList("selectById", map);
        System.out.println(selectById);
    }
}
