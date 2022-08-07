package com.cyf.netty.advance.protocol;

import com.cyf.netty.advance.protocol.endec.MessageCodec;
import com.cyf.netty.advance.protocol.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author 陈一锋
 * @date 2022/8/7 10:20 上午
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new MessageCodec()

        );

        //encode
        LoginRequestMessage loginReq = new LoginRequestMessage("cyf", "123456");
        embeddedChannel.writeOutbound(loginReq);

        //decode 解码
        ByteBuf bf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, loginReq, bf);

        embeddedChannel.writeInbound(bf);
    }
}
