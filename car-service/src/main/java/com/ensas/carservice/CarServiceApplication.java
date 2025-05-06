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

    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository) {
        return args -> {
            List<Car> cars = List.of(
                    Car.builder().brand("BMW").model("M3 GTR E46").image("images/bmw1.jpg").productionYear(new Date()).licensePlate("BMW-123-1").userId(1L).build(),
                    Car.builder().brand("Audi").model("RS7 Sportback").image("images/audi1.jpg").productionYear(new Date()).licensePlate("AUDI-456-2").userId(2L).build(),
                    Car.builder().brand("Toyota").model("Land Cruiser").image("images/toyota1.jpg").productionYear(new Date()).licensePlate("TOYOTA-789-3").userId(3L).build(),
                    Car.builder().brand("Mercedes").model("AMG GT").image("images/mercedes1.jpg").productionYear(new Date()).licensePlate("MERC-321-4").userId(4L).build(),
                    Car.builder().brand("Ford").model("Mustang GT").image("images/ford1.jpg").productionYear(new Date()).licensePlate("FORD-654-5").userId(5L).build(),
                    Car.builder().brand("Nissan").model("GT-R R35").image("images/nissan1.jpg").productionYear(new Date()).licensePlate("NISSAN-987-6").userId(6L).build(),
                    Car.builder().brand("Porsche").model("911 Turbo S").image("images/porsche1.jpg").productionYear(new Date()).licensePlate("PORSCH-159-7").userId(7L).build(),
                    Car.builder().brand("Lamborghini").model("Aventador SVJ").image("images/lamborghini1.jpg").productionYear(new Date()).licensePlate("LAMBO-753-8").userId(8L).build(),
                    Car.builder().brand("Ferrari").model("488 Pista").image("images/ferrari1.jpg").productionYear(new Date()).licensePlate("FERRARI-852-9").userId(9L).build(),
                    Car.builder().brand("Tesla").model("Model S Plaid").image("images/tesla1.jpg").productionYear(new Date()).licensePlate("TESLA-951-0").userId(10L).build()
            );
            carRepository.saveAll(cars);
        };
    }
}
