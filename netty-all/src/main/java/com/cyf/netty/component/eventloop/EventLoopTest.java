package com.cyf.netty.component.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author 陈一锋
 * @date 2022/8/6 10:57 上午
 */
@Slf4j
public class EventLoopTest {
    public static void main(String[] args) {
        //创建一个事件循环组,处理io事件，普通任务，定时任务
        //线程数不设置则默认取cpu核心数*2
        EventLoopGroup el = new NioEventLoopGroup(2);

        //轮训获取eventLoop
        System.out.println(el.next());
        System.out.println(el.next());
        System.out.println(el.next());
        System.out.println(el.next());

        //执行普通任务
        el.next().submit(() -> log.debug("hello"));

        el.next().scheduleAtFixedRate(() -> log.debug("hello schedule"), 1, 1, TimeUnit.SECONDS);
    }
}
