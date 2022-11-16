package com.cyf.netty.rpc.proxy;

import com.cyf.netty.rpc.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author 陈一锋
 * @date 2022/11/16 10:12 下午
 */
public class RpcFactory {

    private RpcClient client;

    public RpcFactory(RpcClient client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("toString".equals(method.getName())){
                            return "";
                        }
                        Request request = new Request();
                        request.setArgs(args);
                        request.setClassName(clazz.getName());
                        request.setMethod(method.getName());
                        //正常是从配置中心获取目标连接信息 先写死
                        return client.sendSync(new InetSocketAddress("localhost", 8888), request, 5000);
                    }
                });
    }
}
