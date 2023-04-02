package com.cyf.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**
 * @author 陈一锋
 * @date 2021/2/2 14:56
 **/
@Aspect
public class AspectJTest {

    @Pointcut("execution(* *.test(..))")
    public void test() {
    }


    @Before("test()")
    public void beforeTest() {
        System.out.println("-----before----");
    }

    @After("test()")
    public void afterTest() {
        System.out.println("------after------");
    }

    @Around("test()")
    public Object aroundTest(ProceedingJoinPoint pjp) {
        System.out.println("around---before1");
        Object o = null;
        try {
            o = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("around---after1");
        return o;
    }

}
