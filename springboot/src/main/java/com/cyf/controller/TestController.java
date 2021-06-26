package com.cyf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈一锋
 * @date 2021/6/14 20:34
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/t1")
    public void t1(){
        System.out.println("t1");
    }
}
