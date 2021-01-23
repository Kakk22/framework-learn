package com.cyf.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @author 陈一锋
 * @date 2021/1/22 10:10
 **/
public class TestEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public TestEvent(Object source) {
        super(source);
    }
}
