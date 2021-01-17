package com.cyf.nettybook.protocol.codec;

import com.cyf.nettybook.protocol.message.Header;
import com.cyf.nettybook.protocol.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 消息编码器
 *
 * @author 陈一锋
 * @date 2021/1/14 20:07
 **/
public final class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    final private MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }

        Header header = msg.getHeader();
        out.writeInt(header.getCrcCode());
        out.writeInt(header.getLength());
        out.writeLong(header.getSessionId());
        out.writeByte(header.getType());
        out.writeByte(header.getPriority());
        out.writeInt(header.getAttachment().size());

        for (Map.Entry<String, Object> entry : header.getAttachment().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            out.writeInt(key.length());
            out.writeBytes(key.getBytes("UTF-8"));
            marshallingEncoder.encode(out, value);
        }

        if (msg.getBody() != null) {
            marshallingEncoder.encode(out, msg.getBody());
        } else {
            out.writeInt(0);
        }
        //设置总长度
        out.setInt(4, out.readableBytes() - 8);
    }

}
