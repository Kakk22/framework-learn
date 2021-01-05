package com.cyf.nettybook.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * nio任务执行
 *
 * @author 陈一锋
 * @date 2021/1/4 20:54
 **/
public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            //初始化选择器
            selector = Selector.open();
            //初始化管道
            serverSocketChannel = ServerSocketChannel.open();
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //注册选择器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:" + port);
        } catch (IOException e) {
            //端口可能被占用 占用则退出应用
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                // 1秒轮询一次
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        //处理请求
                        handleInput(key);
                    } catch (IOException e) {
                        //发生异常关闭管道
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                //多路注册选择器关闭后,所有注册在上面的channel会关闭
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理请求
     *
     * @param key 选择key
     */
    private void handleInput(SelectionKey key) throws IOException {
        //处理请接入的请求
        if (key.isValid()) {
            if (key.isAcceptable()) {
                //这里相当于网络事件的通知,通过accept接受客户端的连接请求并创建SocketChannel实例
                //完成这些,相当于完成三次握手
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                //设置非阻塞
                sc.configureBlocking(false);
                //添加新的选择器
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                //读取客户端的请求消息
                SocketChannel sc = (SocketChannel) key.channel();
                //由于无法得知客户端发送的码流大小,作为例程 开辟1MB的缓存区
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //调用read方法获取请求码流
                //SocketChannel 已经设置为异步非阻塞
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    //将limit设置成position,position设置为0 用于后续对缓存区的读取操作
                    readBuffer.flip();
                    //根据缓存区可读字节个数创建字节数组
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order:" + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(
                            System.currentTimeMillis()
                    ).toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    //对链路进行关闭
                    key.cancel();
                    sc.close();
                } else {
                    //读到0字节 忽略
                    ;
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            //将字节数组复制到缓冲区
            writeBuffer.put(bytes);
            //对缓冲区进行反转
            writeBuffer.flip();
            //将缓冲区中得字节数组发送出去
            //channel为异步非阻塞 不能保证一次能够把需要发送得字节数组发送完
            channel.write(writeBuffer);
        }
    }
}
