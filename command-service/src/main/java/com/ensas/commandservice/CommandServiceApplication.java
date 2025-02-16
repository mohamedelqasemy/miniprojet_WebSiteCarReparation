package com.ensas.commandservice;

import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.entities.CommandDetails;
import com.ensas.commandservice.enums.EnumStatus;
import com.ensas.commandservice.repositories.CommandDetailsRepository;
import com.ensas.commandservice.repositories.CommandRepository;
import com.ensas.commandservice.services.CommandService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class CommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(CommandRepository commandRepository, CommandDetailsRepository commandDetailsRepository) {
        return args -> {
            List<Command> commands = List.of(
                    Command.builder()
                            .date(new Date())
                            .status(EnumStatus.PENDING)
                            .userId(1L)
                            .total(900)
                            .build(),
                    Command.builder()
                            .date(new Date())
                            .status(EnumStatus.APPROVED)
                            .userId(2L)
                            .total(1500)
                            .build()
            );

            commandRepository.saveAll(commands);

            List<CommandDetails> commandDetails = List.of(
                    new CommandDetails(null, commands.get(0), 1L, 2, 450), // 2 équipements à 450 chacun
                    new CommandDetails(null, commands.get(0), 2L, 1, 300), // 1 équipement à 300
                    new CommandDetails(null, commands.get(1), 3L, 3, 500)  // 3 équipements à 500 chacun
            );

            commandDetailsRepository.saveAll(commandDetails);
        };
    }

}
