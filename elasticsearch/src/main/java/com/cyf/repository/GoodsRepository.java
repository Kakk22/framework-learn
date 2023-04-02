package com.cyf.repository;

import com.cyf.domain.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2022/9/29 10:01 下午
 */
@Mapper
public interface GoodsRepository {

    @Select("select * from goods")
    List<Good> selectAll();

}
