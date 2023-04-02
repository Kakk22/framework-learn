package com.cyf.listener;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author 陈一锋
 * @date 2021/1/22 10:10
 **/
public class TestEvent extends ApplicationEvent {

    private String msg;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public TestEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
