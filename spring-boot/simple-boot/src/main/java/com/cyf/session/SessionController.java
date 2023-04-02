package com.cyf.session;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 陈一锋
 * @date 2022/3/10 9:09 下午
 */
@RestController
public class SessionController {

    @RequestMapping("/session")
    public String t1(HttpSession session, HttpServletRequest request) {
        //spring-session 核心类是SessionRepositoryFilter
        //在过滤器中将HttpServletRequest 和 HttpServletRequest 包装了一层SessionRepositoryRequestWrapper SessionRepositoryResponseWrapper
        System.out.println("sessionid=" + session.getId());
        Object attribute = session.getAttribute("111");
        if (attribute == null) {
            session.setAttribute("111", "cyf");
        } else {
            System.out.println("从session中获取111"+attribute);
        }
        return "hello session";
    }
}
