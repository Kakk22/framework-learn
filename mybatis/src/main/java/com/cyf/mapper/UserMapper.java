package com.cyf.mapper;

import com.cyf.model.User;
import org.apache.ibatis.annotations.Select;



/**
 * @author 陈一锋
 * @date 2021/8/7 12:48 上午
 */
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getOne(Integer id);

    @Select("select * from user where id = #{id}")
    User getOne1(Integer id);
}
