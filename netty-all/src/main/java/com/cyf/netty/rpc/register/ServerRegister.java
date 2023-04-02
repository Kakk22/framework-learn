package com.cyf.netty.rpc.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈一锋
 * @date 2022/11/16 10:35 下午
 */
public class ServerRegister {

    private Map<String, Object> serveMap = new HashMap<>();

    public void register(String name, Object object) {
        this.serveMap.put(name, object);
    }

    public Object getServer(String name) {
        return serveMap.get(name);
    }
}
