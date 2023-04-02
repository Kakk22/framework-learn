package com.cyf.status.operator;

import com.cyf.status.enums.OrderStatusEnum;

/**
 * 订单操作状态流转
 *
 * @author 陈一锋
 * @date 2022/5/28 9:36 下午
 */
public interface OrderOperator {

    /**
     * 返回处理类处理状态
     *
     * @return 订单状态
     */
    int getStatus();


    /**
     * 根据状态进行 状态流转
     *
     * @param orderStatus     订单当前状态
     * @param orderStatusEnum 目标状态
     * @return 流转后状态
     */
    int handleEvent(int orderStatus, OrderStatusEnum orderStatusEnum);
}
