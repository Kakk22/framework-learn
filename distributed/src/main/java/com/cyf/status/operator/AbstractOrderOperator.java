package com.cyf.status.operator;

/**
 * @author 陈一锋
 * @date 2022/5/28 9:41 下午
 */
public abstract class AbstractOrderOperator implements OrderOperator {
    protected int status;

    @Override
    public int getStatus() {
        return status;
    }

    /**
     * 设置状态
     */
    public abstract void setStatus();
}
