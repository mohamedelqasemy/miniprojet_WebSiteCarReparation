package com.ensas.equipementservice;

import com.ensas.equipementservice.entities.Equipment;
import com.ensas.equipementservice.repositories.EquipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class EquipementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipementServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EquipmentRepository equipmentRepository) {
        return args -> {
            List<Equipment> equipments = List.of(
                    Equipment.builder()
                            .name("batterie")
                            .description("batterie 12v tres puissant")
                            .price(500.0)
                            .quantity(10L)
                            .image("/images/Equipement.png")
                            .build(),
                    Equipment.builder()
                            .name("moteur")
                            .description("moteur 500 cheval")
                            .price(6700.0)
                            .quantity(5L)
                            .image("/images/Equipement.png")
                            .build(),
                    Equipment.builder()
                            .name("Accessories")
                            .description("Nice accessories")
                            .price(700.0)
                            .quantity(10L)
                            .image("/images/Equipement.png")
                            .build()
            );
            equipmentRepository.saveAll(equipments);
        };
    }
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Utilise le chemin relatif correct pour le dossier partagé
                registry
                        .addResourceHandler("/images/**")
                        .addResourceLocations("file:../uploads/"); // Remarque: "../" pour remonter d'un niveau
            }
        };
    }

}
