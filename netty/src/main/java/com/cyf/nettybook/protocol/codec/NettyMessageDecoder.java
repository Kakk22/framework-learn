package com.cyf.nettybook.protocol.codec;

import com.cyf.nettybook.protocol.message.Header;
import com.cyf.nettybook.protocol.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 解码器
 *
 * @author 陈一锋
 * @date 2021/1/14 21:34
 **/
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }


        Header header = new Header();
        header.setCrcCode(in.readInt());
        header.setLength(in.readInt());
        header.setSessionId(in.readLong());
        header.setType(in.readByte());
        header.setPriority(in.readByte());

        int size = in.readInt();
        if (size > 0) {
            Map<String, Object> att = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                int keyLength = in.readInt();
                byte[] keyBuf = new byte[keyLength];
                in.readBytes(keyBuf);
                String key = new String(keyBuf, StandardCharsets.UTF_8);
                att.put(key, marshallingDecoder.decode(in));
            }
            header.setAttachment(att);
        }
        NettyMessage message = new NettyMessage();
        message.setHeader(header);
        if (in.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(in));
        }
        return message;
    }
}
