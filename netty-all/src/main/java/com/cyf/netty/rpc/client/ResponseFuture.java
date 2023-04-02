package com.cyf.netty.rpc.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 陈一锋
 * @date 2022/11/16 10:01 下午
 */
public class ResponseFuture {

    private CountDownLatch countDownLatch;
    private final static AtomicLong ATOMIC_LONG = new AtomicLong(0);

    private final long uid;

    private Object data;

    public ResponseFuture() {
        countDownLatch = new CountDownLatch(1);
        uid = ATOMIC_LONG.incrementAndGet();
    }

    public void await(int time) {
        try {
            countDownLatch.await(time, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void putResponse(Object data){
        this.data = data;
        countDownLatch.countDown();
    }

    public long getUid() {
        return uid;
    }

    public Object getData() {
        return data;
    }
}
