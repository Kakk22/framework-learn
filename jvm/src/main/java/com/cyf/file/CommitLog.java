package com.cyf.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author 陈一锋
 * @date 2022/12/15 9:33 下午
 */
public class CommitLog {

    private MappedFile mappedFile;

    public CommitLog() {
        try {
            this.mappedFile = new MappedFile("test/file/000000000", 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putMessage(Message msg) {
        //1.消息总长度 4
        //2.body长度 4
        //3.body数据
        //4.topic长度 4
        //5.topic数据
        int totalLength = 4 + 4 + msg.getBody().length + 4 + msg.getTopic().length();
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        buffer.putInt(totalLength);
        buffer.putInt(msg.getBody().length);
        buffer.put(msg.getBody());
        buffer.putInt(msg.getTopic().length());
        buffer.put(msg.getTopic().getBytes(StandardCharsets.UTF_8));
        mappedFile.write(buffer.array());
        buffer = null;
    }

    public ByteBuffer getMessage(int offset, int size) {
        return mappedFile.selectMappedBuffer(offset,size);
    }

    public static void main(String[] args) {
        CommitLog commitLog = new CommitLog();
        Message message = new Message();
        message.setTopic("topic_A");
        message.setBody("hello commitlog".getBytes(StandardCharsets.UTF_8));
        commitLog.putMessage(message);

        ByteBuffer byteBuffer = commitLog.getMessage(0, 34);
        Message res = new Message();
        //总长度
        byteBuffer.getInt();
        int bodySize = byteBuffer.getInt();
        byte[] bodyByte = new byte[bodySize];
        byteBuffer.get(bodyByte);
        res.setBody(bodyByte);
        System.out.println("body:"+new String(bodyByte));
        int topicSize = byteBuffer.getInt();
        byte[] topic = new byte[topicSize];
        byteBuffer.get(topic);
        res.setTopic(new String(topic));
        System.out.println("topic:"+res.getTopic());
    }
}
