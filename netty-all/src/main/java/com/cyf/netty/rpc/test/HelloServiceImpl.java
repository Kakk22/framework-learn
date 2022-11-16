package com.cyf.netty.rpc.test;

/**
 * @author 陈一锋
 * @date 2022/11/16 10:11 下午
 */
public class HelloServiceImpl implements HelloService {


    @Override
    public String hello(String p) {
        return "rpc调用" + System.currentTimeMillis();
    }
}
