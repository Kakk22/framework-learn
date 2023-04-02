package com.cyf.netty.advance.protocol.message;

import com.cyf.netty.advance.im.server.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 陈一锋
 * @date 2022/8/7 10:21 上午
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestMessage extends Message {

    private String username;
    private String password;

    @Override
    public Integer messageType() {
        return LOGIN_REQUEST_CODE;
    }
}
