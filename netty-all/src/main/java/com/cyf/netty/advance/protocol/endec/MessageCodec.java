package com.cyf.netty.advance.protocol.endec;

import com.cyf.netty.advance.im.server.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 自定义协议
 * 1.魔术值 4个字节
 * 2.版本号 1个字节
 * 3.序列号算法 1个字节  0 jdk 1 json
 * 4.业务指令类型 4个字节
 * 5.请求序号 1个字节
 * 6.占位符 1个字节
 * 6.正文长度 4个字节
 * 7.消息正文 n
 *
 * @author 陈一锋
 * @date 2022/8/7 10:23 上午
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    public void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //魔术值
        out.writeBytes(new byte[]{1, 2, 3, 4});
        //版本号
        out.writeByte(1);
        //序列化算法 1个字节
        out.writeByte(0);
        //指令类型
        out.writeInt(msg.messageType());
        //请求序号
        out.writeByte(msg.getSequenceId());
        //占位符号
        out.writeByte(0);
        //正文长度
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] data = bos.toByteArray();
        out.writeInt(data.length);
        //正文内容
        out.writeBytes(data);
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magic = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        int messageType = in.readInt();
        byte sequenceId = in.readByte();
        byte b = in.readByte();
        int contentLength = in.readInt();
        byte[] bytes = new byte[contentLength];
        in.readBytes(bytes);
        if (serializerType == 0) {
            final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            final Message msg = (Message) ois.readObject();
            log.debug("{},{},{},{},{},{},{}", magic, version, serializerType, messageType, sequenceId, b, contentLength);
            log.debug("内容:{}", msg);
            out.add(msg);
        }

    }
}
