package com.ensas.domicileservice;

import com.ensas.domicileservice.entities.RequestHome;
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
                            .address("Casablanca Sebatta")
                            .serviceType("Reparation")
                            .dateDemand(new Date())
                            .status("Waiting")
                            .build()
            );
            requestHomeRepository.saveAll(requests);
        };
    }
}
