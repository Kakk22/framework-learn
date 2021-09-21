package com.cyf.batch;

import cn.hutool.core.collection.ListUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 批量提交处理
 *
 * @author 陈一锋
 * @date 2021/9/21 5:40 下午
 */
public class BatchUtil {

    public static final int MAX_COUNT = 50;

    public static <T, K> void batch(int maxCount, Collection<K> data, Class<T> mapperClazz, BiConsumer<T, K> consumer) {
        int count = Math.max(maxCount, MAX_COUNT);
        batchCount(count, data, mapperClazz, consumer);
    }

    public static <T, K> void batch(Collection<K> data, Class<T> mapperClazz, BiConsumer<T, K> consumer) {
        batchCount(MAX_COUNT, data, mapperClazz, consumer);
    }

    public static <T, K> void batchCount(int maxCount, Collection<K> data, Class<T> mapperClazz, BiConsumer<T, K> consumer) {
        SqlSessionFactory sqlSessionFactory = SpringFactory.getBean(SqlSessionFactory.class);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        T mapper = sqlSession.getMapper(mapperClazz);
        int i = 0;
        for (K d : data) {
            consumer.accept(mapper, d);
            if ((++i) % maxCount == 0) {
                sqlSession.flushStatements();
                i = 0;
            }
        }
        sqlSession.flushStatements();
        sqlSession.close();
    }


}
