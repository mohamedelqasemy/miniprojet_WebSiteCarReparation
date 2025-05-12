package com.ensas.commandservice;

import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.entities.CommandDetails;
import com.ensas.commandservice.enums.EnumStatus;
import com.ensas.commandservice.repositories.CommandDetailsRepository;
import com.ensas.commandservice.repositories.CommandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

@SpringBootApplication
@EnableFeignClients
public class CommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CommandRepository commandRepository, CommandDetailsRepository commandDetailsRepository) {
        return args -> {
            // Créer des commandes pour différents mois
            List<Command> commands = List.of(
                    createCommandForMonth(1, EnumStatus.EN_ATTENTE, 1L, 900), // Janvier
                    createCommandForMonth(3, EnumStatus.EN_COURS, 2L, 1500), // Mars
                    createCommandForMonth(5, EnumStatus.LIVREE, 1L, 1200), // Mai
                    createCommandForMonth(8, EnumStatus.LIVREE, 2L, 800),  // Août
                    createCommandForMonth(11, EnumStatus.EN_COURS, 3L, 1000) // Novembre
            );

            commandRepository.saveAll(commands);

            List<CommandDetails> commandDetails = List.of(
                    new CommandDetails(null, commands.get(0), 1L, 2, 450), // 2 équipements à 450 chacun (Janvier)
                    new CommandDetails(null, commands.get(0), 2L, 1, 300), // 1 équipement à 300 (Janvier)
                    new CommandDetails(null, commands.get(1), 1L, 3, 500), // 3 équipements à 500 chacun (Mars)
                    new CommandDetails(null, commands.get(2), 2L, 2, 600), // 2 équipements à 600 chacun (Mai)
                    new CommandDetails(null, commands.get(3), 1L, 1, 400), // 1 équipement à 400 (Août)
                    new CommandDetails(null, commands.get(4), 3L, 2, 350)  // 2 équipements à 350 (Novembre)
            );

            commandDetailsRepository.saveAll(commandDetails);
        };
    }

    // Méthode pour créer des commandes pour un mois spécifique
    private Command createCommandForMonth(int month, EnumStatus status, Long userId, double total) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1); // Le mois commence à 0 en Java (Janvier est 0, Février est 1, etc.)
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Mettre la date au premier jour du mois
        Date commandDate = calendar.getTime();

        return Command.builder()
                .date(commandDate)
                .status(status)
                .userId(userId)
                .total(total)
                .build();
    }
}
