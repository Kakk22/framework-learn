package com.cyf.netty.advance.im.server.message;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:37 下午
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ChatResponseMessage extends MessageResponse {


    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public Integer messageType() {
        return LOGIN_RESPONSE_CODE;
    }
}
