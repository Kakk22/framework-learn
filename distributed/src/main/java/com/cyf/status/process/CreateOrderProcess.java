package com.cyf.status.process;

import com.cyf.status.dto.OrderDTO;
import com.cyf.status.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 陈一锋
 * @date 2022/5/28 9:59 下午
 */
@Slf4j
@Component
public class CreateOrderProcess extends AbstractOrderProcess {

    @Override
    public void setStatus() {
        this.status = OrderStatusEnum.CREATE_EVENT.getStatus();
    }

    @Override
    public boolean process(OrderDTO orderDTO) {
        // 创建/取消订单对应的数据库修改，mq发送等操作，可以在此处process方法中完成
        log.info("进入创建订单后处理器,:{}", orderDTO);
        return true;
    }
}
