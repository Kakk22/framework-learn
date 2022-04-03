package com.cyf.mapper;

import com.cyf.model.Car;
import com.cyf.model.CarDTO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-03T16:51:44+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_292 (Azul Systems, Inc.)"
)
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDTO car2CartDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTO carDTO = new CarDTO();

        carDTO.setSeatCount( car.getNumberOfSeats() );
        carDTO.setMake( car.getMake() );
        if ( car.getType() != null ) {
            carDTO.setType( car.getType().name() );
        }

        return carDTO;
    }
}
