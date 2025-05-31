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

//    @Bean
//    CommandLineRunner commandLineRunner(ReparationRepository reparationRepository) {
//        return args -> {
//            List<Reparation> reparations = List.of(
//                    // Electric repair
//                    Reparation.builder()
//                            .name("Service électrique")
//                            .description("Répare tous les éléments liés à l'électricité du véhicule, y compris l'alternateur, la batterie et les câblages.")
//                            .type("A domicile")
//                            .dateOfCreation(new Date())
//                            .servicePrice(500.0)
//                            .image("/images/Batterie.png")
//                            .build(),
//
//                    // Oil change
//                    Reparation.builder()
//                            .name("Vidange")
//                            .description("Changement de l'huile moteur et remplacement du filtre à huile pour une performance optimale.")
//                            .type("En atelier")
//                            .dateOfCreation(new Date())
//                            .servicePrice(300.0)
//                            .image("/images/Vidange.png")
//                            .build(),
//
//                    // Suspension repair
//                    Reparation.builder()
//                            .name("Suspension")
//                            .description("Réparation des systèmes de suspension du véhicule, y compris les amortisseurs, ressorts et bras de suspension.")
//                            .type("A domicile")
//                            .dateOfCreation(new Date())
//                            .servicePrice(400.0)
//                            .image("/images/Suspension.png")
//                            .build(),
//
//                    // Braking system repair
//                    Reparation.builder()
//                            .name("Freinage")
//                            .description("Réparation et remplacement des disques de frein, des plaquettes et du liquide de frein.")
//                            .type("En atelier")
//                            .dateOfCreation(new Date())
//                            .servicePrice(450.0)
//                            .image("/images/Freinage.png")
//                            .build(),
//
//                    // Battery replacement
//                    Reparation.builder()
//                            .name("Remplacement de batterie")
//                            .description("Remplacement de la batterie du véhicule pour assurer une alimentation fiable.")
//                            .type("En atelier")
//                            .dateOfCreation(new Date())
//                            .servicePrice(200.0)
//                            .image("/images/Batterie.png")
//                            .build(),
//
//                    // Air conditioning service
//                    Reparation.builder()
//                            .name("Climatisation")
//                            .description("Service et entretien du système de climatisation du véhicule pour garantir son efficacité.")
//                            .type("A domicile")
//                            .dateOfCreation(new Date())
//                            .servicePrice(350.0)
//                            .image("/images/Climatisation.png")
//                            .build(),
//
//                    // Engine diagnostics
//                    Reparation.builder()
//                            .name("Diagnostic moteur")
//                            .description("Diagnostic complet du moteur pour identifier les problèmes de performance et d'efficacité.")
//                            .type("En atelier")
//                            .dateOfCreation(new Date())
//                            .servicePrice(250.0)
//                            .image("/images/Diagnostic.png")
//                            .build(),
//
//                    // Tire repair and replacement
//                    Reparation.builder()
//                            .name("Réparation/Remplacement de pneus")
//                            .description("Réparation des pneus endommagés ou remplacement de pneus usés pour assurer la sécurité de conduite.")
//                            .type("A domicile")
//                            .dateOfCreation(new Date())
//                            .servicePrice(100.0)
//                            .image("/images/Pneus.png")
//                            .build(),
//
//                    // Transmission repair
//                    Reparation.builder()
//                            .name("Réparation de transmission")
//                            .description("Réparation des systèmes de transmission, y compris l'embrayage, la boîte de vitesses et le différentiel.")
//                            .type("A domicile")
//                            .dateOfCreation(new Date())
//                            .servicePrice(700.0)
//                            .image("/images/Transmission.png")
//                            .build(),
//
//                    // Windshield replacement
//                    Reparation.builder()
//                            .name("Remplacement de pare-brise")
//                            .description("Remplacement du pare-brise endommagé pour assurer une bonne visibilité et la sécurité du conducteur.")
//                            .type("En atelier")
//                            .dateOfCreation(new Date())
//                            .servicePrice(300.0)
//                            .image("/images/PareBrise.png")
//                            .build(),
//                    Reparation.builder()
//                            .name("Remplacement de pare-brise")
//                            .description("Remplacement du pare-brise endommagé pour assurer une bonne visibilité et la sécurité du conducteur.")
//                            .type("En atelier")
//                            .dateOfCreation(new Date())
//                            .servicePrice(300.0)
//                            .image("/images/PareBrise.png")
//                            .build(),
//                    Reparation.builder()
//                            .name("Remplacement de pare-brise")
//                            .description("Remplacement du pare-brise endommagé pour assurer une bonne visibilité et la sécurité du conducteur.")
//                            .type("A domicile")
//                            .dateOfCreation(new Date())
//                            .servicePrice(300.0)
//                            .image("/images/PareBrise.png")
//                            .build(),
//                    Reparation.builder()
//                            .name("Remplacement de pare-brise")
//                            .description("Remplacement du pare-brise endommagé pour assurer une bonne visibilité et la sécurité du conducteur.")
//                            .type("En atelier")
//                            .dateOfCreation(new Date())
//                            .servicePrice(300.0)
//                            .image("/images/PareBrise.png")
//                            .build()
//
//            );
//            reparationRepository.saveAll(reparations);
//        };
//    }
//
//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry
//                        .addResourceHandler("/images/**")
//                        .addResourceLocations("file:uploads/");
//            }
//        };
//    }

}
