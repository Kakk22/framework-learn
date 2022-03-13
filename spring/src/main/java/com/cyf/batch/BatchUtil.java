package com.cyf.batch;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Collection;
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
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            T mapper = sqlSession.getMapper(mapperClazz);
            int i = 0;
            for (K d : data) {
                consumer.accept(mapper, d);
                if ((++i) % maxCount == 0) {
                    sqlSession.commit();
                    i = 0;
                }
            }
            if (i != 0) {
                sqlSession.commit();
            }
        }
    }


}
