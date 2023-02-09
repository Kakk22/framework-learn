package com.cyf.nio.buffer;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 陈一锋
 * @date 2022/8/2 11:23 下午
 */
public class ByteBufferTest0 {

    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("/Users/chenyifeng/dev/java/project/system-center/netty-all/data.txt").getChannel()) {
            int read = 0 ;
            final ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (read != -1){
                read = channel.read(byteBuffer);
                if (read != -1) {
                    byteBuffer.flip();
                    byteBuffer.compact();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
