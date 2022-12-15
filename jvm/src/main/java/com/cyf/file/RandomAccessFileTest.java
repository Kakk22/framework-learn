package com.cyf.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈一锋
 * @date 2022/12/10 5:14 下午
 */
public class RandomAccessFileTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        RandomAccessFile file = new RandomAccessFile("/Users/chenyifeng/dev/test/testFile.txt", "rw");
        FileChannel fileChannel = file.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 10);
        //技巧
        //1.写之前应该记录当前位点 及 写后更新当前位点
        //2.超过文件大小则重新新建一个文件
        byte[] b = new byte[1024*5];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) i;
        }
        mappedByteBuffer.put(b);

        System.out.println("写入map file 成功");
        TimeUnit.SECONDS.sleep(5);

        // 将map映射文件数据 刷新到磁盘
        mappedByteBuffer.force();

        System.out.println("force 成功");

        file.close();
        fileChannel.close();
    }
}
