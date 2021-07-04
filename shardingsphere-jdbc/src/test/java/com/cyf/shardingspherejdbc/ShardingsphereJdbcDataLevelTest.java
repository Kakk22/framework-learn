package com.cyf.shardingspherejdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyf.shardingspherejdbc.entity.Course;
import com.cyf.shardingspherejdbc.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 陈一锋
 * @date 2021/6/27 13:47
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("data-level")
public class ShardingsphereJdbcDataLevelTest {
    @Autowired
    private CourseMapper courseMapper;
    //------------------水平分库
    @Test
    void testDB1() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java");
            course.setCstatus("up");
            //根据userid分库
            course.setUserId(100L);
            courseMapper.insert(course);
        }
    }

    @Test
    void testDB2() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java");
            course.setCstatus("up");
            //根据userid分库
            course.setUserId(101L);
            courseMapper.insert(course);
        }
    }


    @Test
    void findOne() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 615908396490555392L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }
}
