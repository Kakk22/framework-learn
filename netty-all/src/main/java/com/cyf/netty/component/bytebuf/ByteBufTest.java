package com.cyf.netty.component.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author 陈一锋
 * @date 2022/8/6 7:54 下午
 */
public class ByteBufTest {
    public static void main(String[] args) {
        final ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        System.out.println(buffer);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append("a");
        }
        //自动扩容
        buffer.writeBytes(sb.toString().getBytes());
        System.out.println(buffer);
    }
}
