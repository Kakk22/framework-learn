package com.cyf.netty.rpc;

import com.cyf.netty.rpc.proxy.RpcFactory;
import com.cyf.netty.rpc.register.ServerRegister;
import com.cyf.netty.rpc.test.HelloService;
import com.cyf.netty.rpc.test.HelloServiceImpl;

import java.io.RandomAccessFile;


/**
 * @author 陈一锋
 * @date 2022/11/16 9:56 下午
 */
public class Application {
    public static void main(String[] args) throws InterruptedException {

        ServerRegister serverRegister = new ServerRegister();
        serverRegister.register("com.cyf.netty.rpc.test.HelloService", new HelloServiceImpl());

        RpcServer rpcServer = new RpcServer(serverRegister);
        rpcServer.start();

        RpcClient rpcClient = new RpcClient();
        rpcClient.start();

        RpcFactory rpcFactory = new RpcFactory(rpcClient);

        HelloService helloService = rpcFactory.getProxy(HelloService.class);

        String res = helloService.hello("nihao");
        System.out.println("rpc调用返回结果1:"+res);

        String res1 = helloService.hello("nihao222");
        System.out.println("rpc调用返回结果2"+res1);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
