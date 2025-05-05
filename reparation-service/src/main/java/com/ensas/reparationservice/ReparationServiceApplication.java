package com.ensas.reparationservice;

import com.ensas.reparationservice.entities.Reparation;
import com.ensas.reparationservice.repositories.ReparationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
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
                            .type("a domicile")
                            .dateOfCreation(new Date())
                            .servicePrice(500.0)
                            .image("/images/Batterie.png")
                            .build(),
                    Reparation.builder()
                            .name("Vidange")
                            .description("Repare all things that have a relation with Vidange.")
                            .type("en atelier")
                            .dateOfCreation(new Date())
                            .servicePrice(500.0)
                            .image("/images/Vidange.png")
                            .build(),
                    Reparation.builder()
                            .name("Suspension")
                            .description("Repare all things that have a relation with Suspension.")
                            .type("en atelier")
                            .dateOfCreation(new Date())
                            .servicePrice(500.0)
                            .image("/images/Suspension.png")
                            .build(),
                    Reparation.builder()
                            .name("Freinage")
                            .description("Repare all things that have a relation with Freinage.")
                            .type("a domicile")
                            .dateOfCreation(new Date())
                            .servicePrice(500.0)
                            .image("/images/Freinage.png").build()

            );
            reparationRepository.saveAll(reparations);
        };
    }
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry
                        .addResourceHandler("/images/**")
                        .addResourceLocations("file:uploads/");
            }
        };
    }

}
