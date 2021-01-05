package com.cyf.nettybook.nio.server.handler;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author 陈一锋
 * @date 2021/1/5 18:27
 **/
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsynTimeServerHandler attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsynTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
