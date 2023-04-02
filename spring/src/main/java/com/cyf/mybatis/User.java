package com.cyf.mybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 陈一锋
 * @date 2021/2/22 20:51
 **/
@Data
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
