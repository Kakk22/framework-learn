package com.cyf.netty.advance.im.server.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:46 下午
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatRequestMessage extends Message {

    private String to;
    private String content;

    @Override
    public Integer messageType() {
        return CHAT_REQUEST_CODE;
    }
}
