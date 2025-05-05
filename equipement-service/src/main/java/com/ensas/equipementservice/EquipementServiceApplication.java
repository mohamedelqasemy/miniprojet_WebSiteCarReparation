package com.ensas.equipementservice;

import com.ensas.equipementservice.entities.Comment;
import com.ensas.equipementservice.entities.Equipment;
import com.ensas.equipementservice.entities.ImageEquipment;
import com.ensas.equipementservice.entities.Rating;
import com.ensas.equipementservice.repositories.EquipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class EquipementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipementServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EquipmentRepository equipmentRepository) {
        return args -> {
            // Equipment 1: Batterie
            Equipment batterie = Equipment.builder()
                    .name("Batterie 12V")
                    .description("Batterie 12V très puissante pour les véhicules utilitaires.")
                    .price(500.0)
                    .quantity(10L)
                    .build();

            batterie.setImages(List.of(
                    ImageEquipment.builder().imageLink("https://example.com/images/batterie1.jpg").equipment(batterie).build(),
                    ImageEquipment.builder().imageLink("https://example.com/images/batterie2.jpg").equipment(batterie).build()
            ));

            batterie.setRatings(List.of(
                    Rating.builder().stars(4).userId(1L).equipment(batterie).build(),
                    Rating.builder().stars(5).userId(2L).equipment(batterie).build()
            ));

            batterie.setComments(List.of(
                    Comment.builder().text("Excellent produit, je recommande.").userId(1L).equipment(batterie).build(),
                    Comment.builder().text("Très bonne performance.").userId(2L).equipment(batterie).build()
            ));

            // Equipment 2: Moteur
            Equipment moteur = Equipment.builder()
                    .name("Moteur V8")
                    .description("Moteur 500 chevaux, idéal pour les voitures sportives.")
                    .price(6700.0)
                    .quantity(5L)
                    .build();

            moteur.setImages(List.of(
                    ImageEquipment.builder().imageLink("https://example.com/images/moteur1.jpg").equipment(moteur).build()
            ));

            moteur.setRatings(List.of(
                    Rating.builder().stars(3).userId(3L).equipment(moteur).build()
            ));

            moteur.setComments(List.of(
                    Comment.builder().text("Bruyant mais très puissant.").userId(3L).equipment(moteur).build()
            ));

            equipmentRepository.saveAll(List.of(batterie, moteur));
        };
    }

}
