package com.cyf.status.process;

import com.cyf.status.enums.OrderStatusEnum;

/**
 * @author 陈一锋
 * @date 2022/5/28 9:31 下午
 */
public abstract class AbstractOrderProcess implements OrderProcess{

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
