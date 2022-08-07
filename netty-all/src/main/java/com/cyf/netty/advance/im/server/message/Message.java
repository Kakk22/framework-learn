package com.cyf.netty.advance.im.server.message;

import java.io.Serializable;

/**
 * @author 陈一锋
 * @date 2022/8/7 10:20 上午
 */
public abstract class Message implements Serializable {

    //默认jdk
    protected int sequenceId = 0;

    public static final int LOGIN_REQUEST_CODE = 0;
    public static final int LOGIN_RESPONSE_CODE = 1;
    public static final int CHAT_REQUEST_CODE = 2;
    public static final int CHAT_RESPONSE_CODE = 3;


    public abstract Integer messageType();


    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }
}
