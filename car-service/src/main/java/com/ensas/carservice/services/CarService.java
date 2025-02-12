package com.ensas.carservice.services;

import com.ensas.carservice.dtos.CarDto;
import com.ensas.carservice.entities.Car;
import com.ensas.carservice.mappers.CarMapper;
import com.ensas.carservice.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    
    public List<CarDto> getAllCars(){
        List<Car> cars = carRepository.findAll();
        return CarMapper.toCarDtoList(cars);
    }
    
    public CarDto getCarById(Long id){
        return carRepository.findById(id)
                .map(CarMapper::toCarDto)
                .orElseThrow(() -> new RuntimeException("Voiture non trouvé"));
    }

    public CarDto createCar(CarDto carDto){
        Car car = CarMapper.toCar(carDto);
        car = carRepository.save(car);
        return CarMapper.toCarDto(car);
    }

    @Transactional
    public CarDto updateCar(Long id,CarDto carDto) {
        if (carDto == null || id == null) {
            throw new IllegalArgumentException("Les données de la Voiture ne peuvent pas être nulles");
        }

        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voiture non trouvé"));
        
        if (carDto.getBrand() != null) {
            existingCar.setBrand(carDto.getBrand());
        }
        if (carDto.getModel() != null) {
            existingCar.setModel(carDto.getModel());
        }
        if (carDto.getLicensePlate() != null) {
            existingCar.setLicensePlate(carDto.getLicensePlate());
        }
        if (carDto.getImage() != null) {
            existingCar.setImage(carDto.getImage());
        }
        if (carDto.getProductionYear() != null) {
            existingCar.setProductionYear(carDto.getProductionYear());
        }

        return CarMapper.toCarDto(existingCar);
    }

    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Voiture non trouvé");
        }
        carRepository.deleteById(id);
    }
    
    public CarDto defaultCarDto(){
        return CarDto.builder()
                .id(0L)
                .brand("Not Available")
                .model("Not Available")
                .image("Not Available")
                .productionYear(new Date())
                .licensePlate("Not Available")
                .userId(0L)
                .build();
    }
    
}
