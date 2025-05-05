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
<<<<<<< HEAD
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
                            .image("/images/Freinage.png")
=======
                            .name("Service Électrique")
                            .description("Répare tout ce qui est en relation avec l'électricité.")
                            .servicePrice(500.0)
                            .build(),
                    Reparation.builder()
                            .name("Réparation de Carrosserie")
                            .description("Répare les bosses, rayures et autres dommages extérieurs des véhicules.")
                            .servicePrice(700.0)
                            .build(),
                    Reparation.builder()
                            .name("Changement d'Huile")
                            .description("Service de vidange et remplacement de l'huile moteur avec filtre.")
                            .servicePrice(100.0)
                            .build(),
                    Reparation.builder()
                            .name("Diagnostic Moteur")
                            .description("Diagnostic complet du moteur et des composants électroniques.")
                            .servicePrice(300.0)
                            .build(),
                    Reparation.builder()
                            .name("Réparation du Système de Freinage")
                            .description("Remplacement des plaquettes, disques et entretien du système de freinage.")
                            .servicePrice(400.0)
                            .build(),
                    Reparation.builder()
                            .name("Réparation Climatisation")
                            .description("Recharge et réparation du système de climatisation du véhicule.")
                            .servicePrice(350.0)
                            .build(),
                    Reparation.builder()
                            .name("Réparation de Pneus")
                            .description("Changement et équilibrage des pneus.")
                            .servicePrice(150.0)
>>>>>>> 23638f01b5568e5f7eb146eed9c49ef681c2abf9
                            .build()
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
