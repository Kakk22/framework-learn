package com.cyf.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 陈一锋
 * @date 2022/12/12 11:20 下午
 */
public class MappedFile {

    private MappedByteBuffer mappedByteBuffer;
    private FileChannel fileChannel;

    private int fileSize;
    private AtomicInteger writePoint = new AtomicInteger(0);

    public MappedFile(String fileName, int fileSize) throws IOException {
        this.fileSize = fileSize;
        File file = new File(fileName);
        String path = file.getParent();
        if (path != null) {
            File parentFile = new File(path);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
            this.fileChannel = randomAccessFile.getChannel();
            this.mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件:" + fileName);
            throw e;
        } catch (IOException e) {
            System.out.println("IO异常:" + fileName);
            throw e;
        }
    }

    public void write(byte[] bytes) {
        //比较当前写指针位点与文件大小
        int curPoint = writePoint.get();
        if (curPoint >= fileSize) {
            throw new RuntimeException("文件已写满:" + curPoint);
        }
        //写入数据
        ByteBuffer buffer = mappedByteBuffer.slice();
        buffer.position(curPoint);
        //写数据进文件map 缓冲 正在写入磁盘需要调用mappedByteBuffer.force
        buffer.put(bytes);
        this.writePoint.addAndGet(bytes.length);
    }

    public ByteBuffer selectMappedBuffer(int offset, int size) {
        int point = writePoint.get();
        if (offset < point && offset >= 0) {
            ByteBuffer byteBuffer = mappedByteBuffer.slice();
            byteBuffer.position(offset);
            ByteBuffer newBuffer = byteBuffer.slice();
            newBuffer.limit(size);
            return newBuffer;
        }
        return null;
    }

    public ByteBuffer selectMappedBuffer(int offset) {
        int point = writePoint.get();
        if (offset < point && offset >= 0) {
            ByteBuffer byteBuffer = mappedByteBuffer.slice();
            byteBuffer.position(offset);
            ByteBuffer newBuffer = byteBuffer.slice();
            newBuffer.limit(point - offset);
            return newBuffer;
        }
        return null;
    }

}
