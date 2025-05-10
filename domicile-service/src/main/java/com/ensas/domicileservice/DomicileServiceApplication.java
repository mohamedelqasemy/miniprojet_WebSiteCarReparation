package com.ensas.domicileservice;

import com.ensas.domicileservice.entities.RequestHome;
import com.ensas.domicileservice.enums.EnumStatus;
import com.ensas.domicileservice.repositories.RequestHomeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class DomicileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomicileServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RequestHomeRepository requestHomeRepository) {
        return args -> {
            List<RequestHome> requests = List.of(
                    RequestHome.builder()
                            .userId(1L)
                            .carId(101L)
                            .serviceId(List.of(1L, 2L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(102L)
                            .serviceId(List.of(3L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(103L)
                            .serviceId(List.of(1L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(104L)
                            .serviceId(List.of(2L, 4L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(105L)
                            .serviceId(List.of(5L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(106L)
                            .serviceId(List.of(1L, 2L, 3L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(107L)
                            .serviceId(List.of(2L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(108L)
                            .serviceId(List.of(3L, 4L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(109L)
                            .serviceId(List.of(5L, 1L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(110L)
                            .serviceId(List.of(4L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(111L)
                            .serviceId(List.of(2L, 3L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build(),
                    RequestHome.builder()
                            .userId(1L)
                            .carId(112L)
                            .serviceId(List.of(1L, 5L))
                            .address("Casablanca Sebatta")
                            .dateDemand(new Date())
                            .status(EnumStatus.Pending)
                            .build()
            );

            requestHomeRepository.saveAll(requests);
        };
    }
}
