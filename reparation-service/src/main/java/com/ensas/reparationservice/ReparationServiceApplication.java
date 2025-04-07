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
                            .build()
            );
            reparationRepository.saveAll(reparations);
        };
    }

}
