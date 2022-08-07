package com.cyf.netty.advance.im.server.message;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:37 下午
 */
public class LoginResponseMessage extends MessageResponse {


    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public Integer messageType() {
        return LOGIN_RESPONSE_CODE;
    }
}
