package com.cyf.status.enums;

import lombok.Getter;

/**
 * @author 陈一锋
 * @date 2022/5/28 9:15 下午
 */
@Getter
public enum OrderStatusEnum {
    /**
     * 订单状态
     */
    CREATE_EVENT(1, "创建订单"),
    FORMAL_EVENT(2, "正式订单"),
    NEED_PAY(3, "待支付"),
    PAY_DONE(4, "已支付"),
    ORDER_FINISHED(5, "订单已完成"),

    ORDER_CANCEL(6, "订单已取消");

    OrderStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private final int status;

    private final String desc;
}
