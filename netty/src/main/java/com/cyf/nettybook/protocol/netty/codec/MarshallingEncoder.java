package com.cyf.nettybook.protocol.netty.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;


/**
 * 消息体编码
 *
 * @author 陈一锋
 * @date 2021/1/14 20:10
 **/
public class MarshallingEncoder {

    private final static byte[] LENGTH_PLACEHOLDER = new byte[4];

    private Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        this.marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    public void encode(ByteBuf out, Object msg) throws IOException {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }
}
