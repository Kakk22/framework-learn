package com.cyf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 陈一锋
 * @date 2022/4/3 4:11 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private String make;
    private int seatCount;
    private String type;

}
