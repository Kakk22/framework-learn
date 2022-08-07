package com.cyf.netty.advance.im.server.message;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:37 下午
 */
public abstract class MessageResponse extends Message {

    protected boolean success;
    protected String reason;

    public MessageResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
