package com.cyf.nettybook.serializable.jdk;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author 陈一锋
 * @date 2021/1/7 14:03
 **/
@Getter
@Setter
public class UserInfo implements Serializable {

    private String userName;
    private int userId;

    public UserInfo buildUsername(String userName) {
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserId(int userId) {
        this.userId = userId;
        return this;
    }


    /**
     * 编码
     *
     * @return /
     */
    public byte[] codeC() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

}
