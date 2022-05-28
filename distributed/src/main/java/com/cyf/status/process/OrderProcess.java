package com.cyf.status.process;

import com.cyf.status.dto.OrderDTO;

/**
 * 订单处理器
 *
 * @author 陈一锋
 * @date 2022/5/28 9:31 下午
 */
public interface OrderProcess {
    /**
     * 返回处理类处理状态
     *
     * @return 订单状态
     */
    int getStatus();

    /**
     * 处理方法
     *
     * @param orderDTO 订单数据
     * @return /
     */
    boolean process(OrderDTO orderDTO);
}
