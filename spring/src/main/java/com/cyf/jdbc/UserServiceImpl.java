package com.cyf.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/2/20 20:53
 **/
public class UserServiceImpl implements UserService {

    private JdbcTemplate jdbcTemplate;

    @Override
    @SuppressWarnings("all")
    public void save(User user) {
        jdbcTemplate.update("insert into  user(name,age,sex) values (?,?,?)",
                new Object[]{user.getName(),user.getAge(),user.getSex()},
                new int[]{Types.VARCHAR,Types.INTEGER,Types.VARCHAR});
    }

    @Override
    @SuppressWarnings("all")
    public List<User> getUsers() {
        return jdbcTemplate.query("select * from user",new UserRowMapper());
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
