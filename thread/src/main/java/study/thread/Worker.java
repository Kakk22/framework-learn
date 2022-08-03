package study.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author 陈一锋
 * @date 2022/6/5 7:38 下午
 */
public class Worker extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
        this.acquire(arg);
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }
}
