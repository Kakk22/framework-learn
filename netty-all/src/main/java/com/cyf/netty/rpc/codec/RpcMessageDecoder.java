package com.cyf.netty.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 *
 *  * 自定义协议
 *  * 1.魔术值 4个字节
 *  * 2.版本号 1个字节
 *  * 3.序列号算法 1个字节  0 jdk 1 json
 *  * 4.业务指令类型 4个字节
 *  * 5.请求序号 1个字节
 *  * 6.占位符 1个字节
 *  * 6.正文长度 4个字节
 *  * 7.消息正文 n
 *
 *
 * @author 陈一锋
 * @date 2022/8/14 2:25 下午
 */
public class RpcMessageDecoder extends ByteToMessageCodec<ByteBuf> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
