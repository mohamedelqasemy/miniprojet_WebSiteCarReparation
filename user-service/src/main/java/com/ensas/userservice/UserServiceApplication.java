package com.ensas.userservice;

import com.ensas.userservice.entities.User;
import com.ensas.userservice.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            List<User> users = List.of(
                    User.builder()
                            .firstname("John1")
                            .lastname("Doe1")
                            .email("john1.doe@gmail.com")
                            .password("password1")
                            .role("ADMIN")
                            .enabled(true)
                            .created(new Date())
                    .build(),
                    User.builder()
                            .firstname("John2")
                            .lastname("Doe2")
                            .email("john2.doe@gmail.com")
                            .password("password2")
                            .role("USER")
                            .enabled(true)
                            .created(new Date())
                            .build()

            );
            userRepository.saveAll(users);
        };
    }
}
