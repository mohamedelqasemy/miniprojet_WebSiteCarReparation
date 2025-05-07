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
                            .num("0645454545")
                            .role("ADMIN")
                            .enabled(true)
                            .image("/images/Profile.png")
                            .created(new Date())
                    .build(),
                    User.builder()
                            .firstname("John2")
                            .lastname("Doe2")
                            .email("john2.doe@gmail.com")
                            .password("password2")
                            .role("USER")
                            .num("0732333333")
                            .enabled(true)
                            .created(new Date())
                            .image("/images/Profile.png")
                            .build(),
                    User.builder()
                            .firstname("John3")
                            .lastname("Doe3")
                            .email("john3.doe@gmail.com")
                            .password("password3")
                            .role("USER")
                            .enabled(true)
                            .created(new Date())
                            .num("0755667788")
                            .image("/images/Profile.png")
                            .build(),
                    User.builder()
                            .firstname("John4")
                            .lastname("Doe4")
                            .email("john4.doe@gmail.com")
                            .password("password4")
                            .role("USER")
                            .num("0655449999")
                            .enabled(true)
                            .created(new Date())
                            .image("/images/Profile.png")
                            .build(),
                    User.builder()
                            .firstname("John5")
                            .lastname("Doe5")
                            .email("john5.doe@gmail.com")
                            .password("password5")
                            .role("USER")
                            .num("0688453925")
                            .enabled(true)
                            .created(new Date())
                            .image("/images/Profile.png")
                            .build()
            );

            userRepository.saveAll(users);
        };
    }
}
