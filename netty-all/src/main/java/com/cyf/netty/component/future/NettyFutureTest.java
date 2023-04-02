package com.cyf.netty.component.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;


/**
 * @author 陈一锋
 * @date 2022/8/6 5:22 下午
 */
@Slf4j
public class NettyFutureTest {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        final EventLoop loop = group.next();
        final Future<Integer> future = loop.submit(() -> {
            Thread.sleep(1000);
            return 10;
        });
        future.addListener(f -> log.debug("接收到结果:{}", f.getNow()));

    }
}
