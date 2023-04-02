package com.cyf.netty.rpc.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 陈一锋
 * @date 2022/11/16 10:16 下午
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String className;
    private Object[] args;
    private String method;

    private long requestId;
}
