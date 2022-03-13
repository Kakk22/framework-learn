package com.cyf.mapper;

import com.cyf.model.User;
import com.cyf.plugin.Page;
import com.cyf.vo.UserVo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author 陈一锋
 * @date 2021/8/7 12:48 上午
 */
@CacheNamespace
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getOne(Integer id);

    @Select("select * from user where id = #{id}")
    User getOne1(Integer id);

    @Select("select * from user where id = #{0} and name = #{1}")
    User selectByName(Integer id,String name);

    List<UserVo> selectUserAndOrder();

    List<UserVo> selectById(List<Integer> list);
}
