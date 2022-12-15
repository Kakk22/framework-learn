package com.cyf.file;

/**
 * @author 陈一锋
 * @date 2022/12/12 11:36 下午
 */
public class Message {
    private String topic;
    private byte[] body;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
