package com.cyf.mapper;


import com.cyf.model.Car;
import com.cyf.model.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author 陈一锋
 * @date 2022/4/3 4:13 下午
 */
@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDTO car2CartDTO(Car car);
}
