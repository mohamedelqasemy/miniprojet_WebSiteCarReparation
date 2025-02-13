package com.ensas.commandservice;

import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.enums.EnumStatus;
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
    CommandLineRunner commandLineRunner(CommandRepository commandRepository) {
        return args -> {
            List<Command> commands = List.of(
                    Command.builder()
                            .date(new Date())
                            .status(EnumStatus.PENDING)
                            .userId(1L)
                            .total(900)
                            .build()
            );
            commandRepository.saveAll(commands);
        };
    }

}
