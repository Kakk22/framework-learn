package com.cyf.netty.rpc.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author 陈一锋
 * @date 2022/8/14 2:29 下午
 */
public class MessageLengthFieldDecoder extends LengthFieldBasedFrameDecoder {


    public MessageLengthFieldDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(1024, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
