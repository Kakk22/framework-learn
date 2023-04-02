package com.cyf.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈一锋
 * @date 2023/3/6 9:09 下午
 */
@RestController
@RequestMapping("/vue")
public class VueController {

    @GetMapping("/student")
    public List<Student> list() {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("张三", 11));
        list.add(new Student("张三1", 12));
        list.add(new Student("张三2", 13));
        list.add(new Student("张三3", 14));
        return list;
    }

    @Data
    @AllArgsConstructor
    class Student {
        String name;
        Integer age;
    }
}
