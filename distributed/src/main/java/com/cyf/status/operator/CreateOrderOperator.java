package com.cyf.status.operator;

import com.cyf.status.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 陈一锋
 * @date 2022/5/28 10:02 下午
 */
@Slf4j
@Component
public class CreateOrderOperator extends AbstractOrderOperator {

    @Override
    public void setStatus() {
        this.status = OrderStatusEnum.CREATE_EVENT.getStatus();
    }

    @Override
    public int handleEvent(int orderStatus, OrderStatusEnum orderStatusEnum) {
        if (orderStatus != OrderStatusEnum.CREATE_EVENT.getStatus() && orderStatus != OrderStatusEnum.ORDER_CANCEL.getStatus()) {
            throw new IllegalArgumentException(String.format("create operation can't handle the status: %s", orderStatus));
        }
        log.info("进入创建订单状态扭转处理器...");
        switch (orderStatusEnum) {
            case CREATE_EVENT:
                return OrderStatusEnum.FORMAL_EVENT.getStatus();
            case ORDER_CANCEL:
                return OrderStatusEnum.ORDER_CANCEL.getStatus();
            default:
                return getStatus();
        }
    }
}
