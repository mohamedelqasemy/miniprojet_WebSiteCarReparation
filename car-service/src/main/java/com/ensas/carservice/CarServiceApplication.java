package com.ensas.carservice;

import com.ensas.carservice.entities.Car;
import com.ensas.carservice.repositories.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class CarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);}
        @Bean
        CommandLineRunner commandLineRunner(CarRepository carRepository) {
            return args -> {
                List<Car> cars = List.of(
                        Car.builder()
                                .brand("BMW")
                                .model("m3 gtr e46")
                                .image("path")
                                .productionYear(new Date())
                                .licensePlate("1234567890")
                                .userId(1L)
                                .build()
                );
                carRepository.saveAll(cars);
            };
    }
}
