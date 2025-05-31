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
        SpringApplication.run(CarServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(CarRepository carRepository) {
//        return args -> {
//            List<Car> cars = List.of(
//                    Car.builder().brand("BMW").model("M3 GTR E46").image("images/bmw1.jpg").motorisation("1L").kilometers(100F).licensePlate("BMW-123-1").userId(1L).build(),
//                    Car.builder().brand("Audi").model("RS7 Sportback").image("images/audi1.jpg").motorisation("1L").kilometers(100F).licensePlate("AUDI-456-2").userId(2L).build(),
//                    Car.builder().brand("Toyota").model("Land Cruiser").image("images/toyota1.jpg").motorisation("1L").kilometers(100F).licensePlate("TOYOTA-789-3").userId(3L).build(),
//                    Car.builder().brand("Mercedes").model("AMG GT").image("images/mercedes1.jpg").motorisation("1L").kilometers(100F).licensePlate("MERC-321-4").userId(2L).build(),
//                    Car.builder().brand("Ford").model("Mustang GT").image("images/ford1.jpg").motorisation("1L").kilometers(100F).licensePlate("FORD-654-5").userId(3L).build(),
//                    Car.builder().brand("Nissan").model("GT-R R35").image("images/nissan1.jpg").motorisation("1L").kilometers(100F).licensePlate("NISSAN-987-6").userId(5L).build(),
//                    Car.builder().brand("Porsche").model("911 Turbo S").image("images/porsche1.jpg").motorisation("1L").kilometers(100F).licensePlate("PORSCH-159-7").userId(4L).build(),
//                    Car.builder().brand("Lamborghini").model("Aventador SVJ").image("images/lamborghini1.jpg").kilometers(100F).motorisation("1L").licensePlate("LAMBO-753-8").userId(4L).build(),
//                    Car.builder().brand("Ferrari").model("488 Pista").image("images/ferrari1.jpg").motorisation("1L").kilometers(100F).licensePlate("FERRARI-852-9").userId(4L).build(),
//                    Car.builder().brand("Tesla").model("Model S Plaid").image("images/tesla1.jpg").motorisation("1L").kilometers(100F).licensePlate("TESLA-951-0").userId(1L).build()
//            );
//            carRepository.saveAll(cars);
//        };
//    }
}
