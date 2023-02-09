package com.cyf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * @author 陈一锋
 * @date 2021/6/14 20:34
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/t1")
    public void t1(@RequestParam("s") String s){
                 System.out.println(s);
    }
    @RequestMapping("/t12")
    public String t12(String params) {
        return "hello";
    }

    @PostMapping("/t11")
    public String t11(String params) {
        return "hello";
    }


    @RequestMapping("/t2")
    public String t2(@RequestParam("params") String params) {
        return "hello";
    }


    @RequestMapping("/t3")
    public String t3(@RequestBody User user) {
        return "hello";
    }

    @RequestMapping("/t4")
    public String t4(HttpServletRequest request, HttpServletResponse response, Map map) {
        return "hello";
    }

    @RequestMapping("/t5")
    public String t4(InputStream inputStream) {
        return "hello";
    }

    @GetMapping("/t6")
    public String t6(InputStream inputStream) {
        return "hello";
    }


}

class User {
    private Integer id;
    private Integer name;

    public User() {
    }

    public User(Integer id, Integer name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }
}