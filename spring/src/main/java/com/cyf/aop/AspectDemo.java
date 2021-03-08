package com.cyf.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * before around
 * before
 * hello
 * after
 * after around
 * 执行的结果
 * @author 陈一锋
 * @date 2021/2/12 20:39
 **/
@Aspect
public class AspectDemo {

    /**
     * 所有类的hello方法会被执行aop
     */
    @Pointcut("execution(* *.hello(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("before around");
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("after around");
        return result;
    }
}
