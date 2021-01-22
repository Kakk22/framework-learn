package com.cyf.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author 陈一锋
 * @date 2021/1/22 10:49
 **/
@Component
public class TestListener implements ApplicationListener<TestEvent> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(TestEvent event) {
        // 接收到事件
        System.out.println("接收到事件通知:"+event.getClass().getName());
        System.out.println(event.getSource());
    }
}
