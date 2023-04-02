package com.cyf.model;

import java.io.Serializable;

/**
 * @author 陈一锋
 * @date 2021/8/22 9:58 下午
 */
public class Order implements Serializable {
    private Integer orderId;
    private Integer userId;
    private String orderCode;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
