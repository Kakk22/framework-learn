package com.cyf.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 陈一锋
 * @date 2021/2/20 20:49
 **/
public class UserRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getString("sex"));
    }
}
