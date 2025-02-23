package com.ensas.historiquepannesservice;

import com.ensas.historiquepannesservice.entities.BreakdownHistory;
import com.ensas.historiquepannesservice.feign.CarRestClient;
import com.ensas.historiquepannesservice.models.CarDto;
import com.ensas.historiquepannesservice.repositories.BreakdownHistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class HistoriquePannesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoriquePannesServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(BreakdownHistoryRepository breakdownHistoryRepository,
                                        CarRestClient carRestClient){

        return args -> {
            Collection<CarDto> cars = carRestClient.getAllCars().getContent();
            System.out.println(cars.size());

            cars.forEach(car -> {
                BreakdownHistory history = BreakdownHistory.builder()
                        .datePanne(new Date())
                        .isRepaired(false)
                        .description("panne dans le batterie")
                        .carId(car.getId())
                        .build();
                breakdownHistoryRepository.save(history);
            });
        };
    }
}
