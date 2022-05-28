package com.cyf.status.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author 陈一锋
 * @date 2022/5/28 9:54 下午
 */
@Data
@Builder
public abstract class OrderDTO {
    private Integer orderId;
    private Integer status;
}
