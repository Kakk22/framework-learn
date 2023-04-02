package com.cyf.netty.advance.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author 陈一锋
 * @date 2022/8/6 11:34 下午
 */
public class LengthFieldDecoderTest {
    public static void main(String[] args) {
//        messageV1();
        messageV2();
    }


    /**
     * 消息协议是 内容长度4个字节+内容
     */
    private static void messageV1() {
        final EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 0),
                new LoggingHandler(LogLevel.DEBUG)
        );

        //消息协议 内容长度4个字节  + 内容
        final ByteBuf bf = ByteBufAllocator.DEFAULT.buffer();
        write2Buf(bf, "hello,lengthFieldDecoder");
        write2Buf(bf, "haha!!");
        embeddedChannel.writeInbound(bf);
    }

    /**
     * 消息协议是 内容长度4个字节 + 1个字节版本号+内容
     */
    private static void messageV2() {
        final EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 4),
                new LoggingHandler(LogLevel.DEBUG)
        );

        //消息协议 内容长度4个字节  + 内容
        final ByteBuf bf = ByteBufAllocator.DEFAULT.buffer();
        write2BufV2(bf, "hello,lengthFieldDecoder", (byte) '1');
        write2BufV2(bf, "haha!!", (byte) '1');
        embeddedChannel.writeInbound(bf);
    }

    private static void write2Buf(ByteBuf byteBuf, String content) {
        final int length = content.getBytes().length;
        byteBuf.writeInt(length);
        byteBuf.writeBytes(content.getBytes());
    }

    private static void write2BufV2(ByteBuf byteBuf, String content, byte version) {
        final int length = content.getBytes().length;
        byteBuf.writeInt(length);
        byteBuf.writeByte(version);
        byteBuf.writeBytes(content.getBytes());
    }
}
