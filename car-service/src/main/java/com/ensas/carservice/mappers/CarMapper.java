package com.ensas.carservice.mappers;

import com.ensas.carservice.dtos.CarDto;
import com.ensas.carservice.entities.Car;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarMapper {
    public static CarDto toCarDto(Car car){
        if (car == null)
            return null;
        return CarDto.builder()
                .id(car.getId())
                .licensePlate(car.getLicensePlate())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .productionYear(car.getProductionYear())
                .userId(car.getUserId())
                .build();
    }

    public static Car toCar(CarDto carDto){
        if (carDto == null)
            return null;
        return Car.builder()
                .id(carDto.getId())
                .licensePlate(carDto.getLicensePlate())
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .image(carDto.getImage())
                .productionYear(carDto.getProductionYear())
                .userId(carDto.getUserId())
                .build();
    }

    public static List<CarDto> toCarDtoList(List<Car> carList){
        if (carList == null)
            return null;
        return carList.stream().map(CarMapper::toCarDto).collect(Collectors.toList());
    }

    public static List<Car> toCarList(List<CarDto> carDtoList){
        if (carDtoList == null)
            return null;
        return carDtoList.stream().map(CarMapper::toCar).collect(Collectors.toList());
    }
}
