package com.cyf.gateway.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 陈一锋
 * @date 2022/5/29 4:21 下午
 */
//@Component
public class TestValue {
    @Value("${time}")
    private String time;

}
