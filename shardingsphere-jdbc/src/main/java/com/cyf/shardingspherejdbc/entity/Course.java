package com.cyf.shardingspherejdbc.entity;

import lombok.Data;

/**
 * @author 陈一锋
 * @date 2021/6/26 13:28
 **/
@Data
public class Course {
    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;
}
