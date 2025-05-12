package com.ensas.domicileservice;

import com.ensas.domicileservice.dtos.RequestHomeRequest;
import com.ensas.domicileservice.entities.RequestHome;
import com.ensas.domicileservice.enums.EnumStatus;
import com.ensas.domicileservice.repositories.RequestHomeRepository;
import com.ensas.domicileservice.services.RequestHomeService;
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
    CommandLineRunner commandLineRunner(RequestHomeService requestHomeService) {
        return args -> {
            List<RequestHomeRequest> requestDtos = List.of(
                    new RequestHomeRequest("Toyota", "Yaris", "Essence", 120000f, "D-42235", "Casablanca Sebatta", 1L, List.of(1L, 2L), new Date()),
                    new RequestHomeRequest("Peugeot", "208", "Diesel", 80000f, "D-42236", "Casablanca Sebatta", 1L, List.of(3L), new Date()),
                    new RequestHomeRequest("Renault", "Clio", "Hybride", 50000f, "D-42237", "Casablanca Sebatta", 1L, List.of(1L), new Date()),
                    new RequestHomeRequest("Dacia", "Logan", "Essence", 60000f, "D-42238", "Casablanca Sebatta", 1L, List.of(2L, 4L), new Date()),
                    new RequestHomeRequest("Hyundai", "i10", "Diesel", 75000f, "D-42239", "Casablanca Sebatta", 1L, List.of(5L), new Date()),
                    new RequestHomeRequest("Ford", "Fiesta", "Essence", 90000f, "D-42240", "Casablanca Sebatta", 1L, List.of(1L, 2L, 3L), new Date()),
                    new RequestHomeRequest("Kia", "Picanto", "Diesel", 70000f, "D-42241", "Casablanca Sebatta", 1L, List.of(2L), new Date()),
                    new RequestHomeRequest("Seat", "Ibiza", "Essence", 85000f, "D-42242", "Casablanca Sebatta", 1L, List.of(3L, 4L), new Date()),
                    new RequestHomeRequest("Volkswagen", "Polo", "Hybride", 95000f, "D-42243", "Casablanca Sebatta", 1L, List.of(5L, 1L), new Date()),
                    new RequestHomeRequest("Chevrolet", "Spark", "Essence", 67000f, "D-42244", "Casablanca Sebatta", 1L, List.of(4L), new Date()),
                    new RequestHomeRequest("Fiat", "Punto", "Diesel", 78000f, "D-42245", "Casablanca Sebatta", 1L, List.of(2L, 3L), new Date()),
                    new RequestHomeRequest("Nissan", "Micra", "Essence", 69000f, "D-42246", "Casablanca Sebatta", 1L, List.of(1L, 5L), new Date())
            );

            requestDtos.forEach(dto -> {
                try {
                    RequestHome created = requestHomeService.createRequestHome(dto);
                    System.out.println("RequestHome created with ID: " + created.getId());
                } catch (Exception e) {
                    System.err.println("Failed to create RequestHome: " + e.getMessage());
                }
            });
        };
    }

}
