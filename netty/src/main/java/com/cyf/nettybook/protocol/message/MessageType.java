package com.cyf.nettybook.protocol.message;

import lombok.Getter;

/**
 * @author 陈一锋
 * @date 2021/1/15 20:48
 **/
@Getter
public enum MessageType {


    /**
     * 业务消息请求
     */
    BUSINESS_RES((byte) 0),
    /**
     * 业务消息回复
     */
    BUSINESS_RESP(((byte) 1)),
    /**
     * 即是请求又是响应
     */
    BUSINESS_ALL(((byte) 2)),
    /**
     * 握手请求
     */
    LOGIN_RES((byte) 3),
    /**
     * 握手回复
     */
    LOGIN_RESP((byte) 4),
    /**
     * 心跳请求
     */
    HEART_RES((byte) 5),

    /**
     * 心跳回复
     */
    HEART_RESP((byte) 6);

    private final byte type;


    MessageType(byte type) {
        this.type = type;
    }
}
