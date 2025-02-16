package com.ensas.reparationservice;

import com.ensas.reparationservice.entities.Reparation;
import com.ensas.reparationservice.repositories.ReparationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ReparationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReparationServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ReparationRepository reparationRepository) {
        return args -> {
            List<Reparation> reparations = List.of(
                    Reparation.builder()
                            .name("Electrique service")
                            .description("Repare all things that have a relation with electrics.")
                            .servicePrice(500.0)
                            .build()
            );
            reparationRepository.saveAll(reparations);
        };
    }

}
