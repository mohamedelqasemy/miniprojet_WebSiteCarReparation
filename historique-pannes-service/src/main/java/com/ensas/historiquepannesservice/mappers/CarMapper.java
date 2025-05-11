package com.ensas.historiquepannesservice.mappers;


import com.ensas.historiquepannesservice.models.Car;
import com.ensas.historiquepannesservice.models.CarDto;

import java.util.List;
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
                .kilometers(car.getKilometers())
                .motorisation(car.getMotorisation())
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
                .kilometers(carDto.getKilometers())
                .motorisation(carDto.getMotorisation())
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
