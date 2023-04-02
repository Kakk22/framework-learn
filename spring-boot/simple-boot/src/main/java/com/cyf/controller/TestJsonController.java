package com.cyf.controller;

import com.alibaba.fastjson.JSON;
import com.cyf.model.User;
import com.cyf.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈一锋
 * @date 2022/10/21 11:06 下午
 */
@RestController
public class TestJsonController {

    @GetMapping("/testJson")
    public Result<String> testJson(){
        return Result.success(JSON.toJSONString(new User(1,"小明")));
    }
}
