package com.cyf.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @author 陈一锋
 * @date 2022/8/3 9:56 下午
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        final Selector selector = Selector.open();

        final ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        log.info("serverSocketChannel:{}", ssc);
        //将channel注册到selector上
        final SelectionKey sk = ssc.register(selector, 0, null);
        //注册感兴趣事件
        sk.interestOps(SelectionKey.OP_ACCEPT);
        log.info("key:{}", sk);
        ssc.bind(new InetSocketAddress(9091));

        while (true) {
            //堵塞直至有事件发生
            selector.select();
            final Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) it.next().channel();
                serverSocketChannel.accept();
                log.info("连接成功:{}", serverSocketChannel);
            }

        }
    }
}
