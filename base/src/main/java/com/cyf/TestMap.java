package com.cyf;

import com.cyf.enmus.CarType;
import com.cyf.mapper.CarMapper;
import com.cyf.model.Car;
import com.cyf.model.CarDTO;

/**
 * @author 陈一锋
 * @date 2022/4/3 4:33 下午
 */
public class TestMap {

    public static void main(String[] args) {
        Car car = new Car( "Morris", 5, CarType.SEDAN );

        //when
        CarDTO carDto = CarMapper.INSTANCE.car2CartDTO( car );
        System.out.println(carDto);
        System.out.println("--------");
    }
}
