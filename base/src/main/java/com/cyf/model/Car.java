package com.cyf.model;

import com.cyf.enmus.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 陈一锋
 * @date 2022/4/3 4:07 下午
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {
    private String make;
    private int numberOfSeats;
    private CarType type;

}
