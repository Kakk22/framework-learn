package com.cyf.netty.rpc.proxy;

import lombok.Data;

/**
 * @author 陈一锋
 * @date 2022/11/16 10:51 下午
 */
@Data
public class Response {
    private long uid;
    private Object data;
}
