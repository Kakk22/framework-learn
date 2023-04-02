package com.cyf.status.manager;

import com.cyf.status.dto.OrderDTO;
import com.cyf.status.enums.OrderStatusEnum;
import com.cyf.status.operator.OrderOperator;
import com.cyf.status.process.OrderProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 陈一锋
 * @date 2022/5/28 9:18 下午
 */
@Component
public class OrderStatusManager {
    private final Map<Integer, OrderProcess> orderProcessMap = new HashMap<>(16);
    private final Map<Integer, OrderOperator> orderOperatorMap = new HashMap<>(16);

    @Autowired(required = false)
    private void initOrderProcess(List<OrderProcess> list) {
        list.forEach(o -> orderProcessMap.put(o.getStatus(), o));
    }

    @Autowired(required = false)
    private void initOrderOperator(List<OrderOperator> list) {
        list.forEach(o -> orderOperatorMap.put(o.getStatus(), o));
    }

    /**
     * 状态流转方法
     *
     * @param orderId 订单id
     * @param event   流转的订单操作事件
     * @param status  当前订单状态
     * @return 扭转后的订单状态
     */
    public int handleEvent(final int orderId, OrderStatusEnum event, final int status) {
        if (this.isFinalStatus(status)) {
            throw new IllegalArgumentException("handle event can't process final state order.");
        }
        // 获取对应处理器,根据入参状态和时间获取订单流转的结果状态
        OrderOperator orderOperator = this.getStateOperator(event);
        //流转后状态
        int resStatus = orderOperator.handleEvent(status, event);
        // 得到结果状态，在对应的processor中处理订单数据及其相关信息
        OrderProcess orderProcess = getStateProcess(event);
        OrderDTO orderDTO = OrderDTO.builder().orderId(orderId).status(resStatus).build();
        if (orderProcess.process(orderDTO)) {
            throw new IllegalStateException(String.format("订单状态流转失败，订单id:%s", orderId));
        }
        return resStatus;
    }


    /**
     * 根据入参状态枚举实例获取对应的状态后处理器
     *
     * @param event event
     */
    public OrderProcess getStateProcess(OrderStatusEnum event) {
        return Optional.ofNullable(orderProcessMap.get(event.getStatus()))
                .orElseThrow(() -> new IllegalArgumentException("根据订单获取处理类异常:" + event.getStatus()));
    }

    /**
     * 根据入参状态枚举实例获取对应的状态处理器
     *
     * @param event event
     */
    public OrderOperator getStateOperator(OrderStatusEnum event) {
        return Optional.ofNullable(orderOperatorMap.get(event.getStatus()))
                .orElseThrow(() -> new IllegalArgumentException("根据订单获取处理类异常:" + event.getStatus()));
    }


    /**
     * 判断是不是已完成订单
     *
     * @param status 订单状态码
     */
    private boolean isFinalStatus(int status) {
        return OrderStatusEnum.ORDER_FINISHED.getStatus() == status;
    }

}
