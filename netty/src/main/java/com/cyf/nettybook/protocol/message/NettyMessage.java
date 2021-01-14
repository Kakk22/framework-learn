package com.cyf.nettybook.protocol.message;

import lombok.Data;

/**
 * @author 陈一锋
 * @date 2021/1/14 19:58
 **/
@Data
public final class NettyMessage {
    /**
     * 消息头
     */
    private Header header;
    /**
     * 消息体
     */
    private Object body;
}
