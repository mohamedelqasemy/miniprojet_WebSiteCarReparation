package com.ensas.garageservice;

import com.ensas.garageservice.entity.Garage;
import com.ensas.garageservice.repository.GarageRepository;
import com.ensas.garageservice.service.GarageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class GarageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GarageServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(GarageRepository garageRepository) {
        return args -> {
            List<String> villes = Arrays.asList(
                    "Agadir", "Marrakech", "Safi", "Rabat", "Tanger",
                    "Casablanca", "Tinghir", "Zagora", "Ouarzazate",
                    "Tiznit", "Beni Mellal", "Azilal"
            );

            Random random = new Random();

            // Ajoutez des points de repère et des informations supplémentaires pour chaque ville
            List<String> adresses = Arrays.asList(
                    "Rue Mohammed V, Agadir, Maroc",
                    "Avenue Hassan II, Marrakech, Maroc",
                    "Avenue des Nations Unies, Safi, Maroc",
                    "Boulevard Moulay Youssef, Rabat, Maroc",
                    "Rue de la Liberté, Tanger, Maroc",
                    "Avenue Hassan II, Casablanca, Maroc",
                    "Avenue Mohammed V, Tinghir, Maroc",
                    "Avenue Hassan II, Zagora, Maroc",
                    "Boulevard Mohammed V, Ouarzazate, Maroc",
                    "Avenue Hassan II, Tiznit, Maroc",
                    "Avenue Mohamed V, Beni Mellal, Maroc",
                    "Rue Al Massira, Azilal, Maroc"
            );

            for (int i = 0; i < villes.size(); i++) {
                String ville = villes.get(i);
                String adresse = adresses.get(i); // Utiliser une adresse plus complète

                Garage garage = Garage.builder()
                        .nom("Garage " + ville)
                        .adresse(adresse)
                        .telephone("06" + (random.nextInt(90000000) + 10000000))
                        .note(random.nextInt(6)) // note entre 0 et 5
                        .OuvertureDate(new Date(System.currentTimeMillis() - 1000000000L)) // ouverture dans le passé
                        .FermetureDate(null) // encore ouvert
                        .dateCreation(new Date())
                        .image("https://source.unsplash.com/garage" + i) // URL fictive
                        .build();

                garageRepository.save(garage);
            }
        };
    }
}
