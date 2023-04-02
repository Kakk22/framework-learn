package com.cyf.shardingspherejdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyf.shardingspherejdbc.entity.Course;
import com.cyf.shardingspherejdbc.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class ShardingsphereJdbcApplicationTests {

    @Autowired
    private CourseMapper courseMapper;



    //-------------------水平分表
    @Test
    void testAdd() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCname("java" + i);
            course.setCstatus("up");
            course.setUserId(100L);
            courseMapper.insert(course);
        }
    }


    @Test
    void findOne() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 615866324370849793L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }

}
