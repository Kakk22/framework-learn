package com.cyf.nettybook.protocol.codec;

import com.cyf.nettybook.protocol.message.Header;
import com.cyf.nettybook.protocol.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.ValueConverter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 消息编码器
 *
 * @author 陈一锋
 * @date 2021/1/14 20:07
 **/
public final class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    final private MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {

        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        Header header = msg.getHeader();
        sendBuf.writeInt(header.getCrcCode());
        sendBuf.writeInt(header.getLength());
        sendBuf.writeLong(header.getSessionId());
        sendBuf.writeByte(header.getType());
        sendBuf.writeByte(header.getPriority());
        sendBuf.writeInt(header.getAttachment().size());

        for (Map.Entry<String, Object> entry : header.getAttachment().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            sendBuf.writeInt(key.length());
            sendBuf.writeBytes(key.getBytes("UTF-8"));
            marshallingEncoder.encode(sendBuf, value);
        }

        if (msg.getBody() != null) {
            marshallingEncoder.encode(sendBuf, msg.getBody());
        } else {
            sendBuf.writeInt(0);
            //设置总长度
            sendBuf.setInt(4, sendBuf.readableBytes());
        }
    }
}
