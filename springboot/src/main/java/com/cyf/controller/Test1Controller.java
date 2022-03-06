package com.cyf.controller;

import com.cyf.common.CosmoController;
import com.cyf.common.ExceptionCodeEnum;
import com.cyf.execption.BizException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 陈一锋
 * @date 2022/3/6 7:50 下午
 */
@CosmoController
@RequestMapping("/api/v1/test")
public class Test1Controller {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
    public User user() {
        return new User(1,2);
    }

    @GetMapping("/ex")
    public User ex(String p) {
        if ("1".equalsIgnoreCase(p)){
            throw new BizException(ExceptionCodeEnum.ERROR);
        }
        return new User(1,2);
    }
}
