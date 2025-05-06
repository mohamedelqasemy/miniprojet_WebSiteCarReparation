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
                    User.builder().firstname("John1").lastname("Doe1").email("john1.doe@gmail.com").password("password1").role("ADMIN").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John2").lastname("Doe2").email("john2.doe@gmail.com").password("password2").role("USER").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John3").lastname("Doe3").email("john3.doe@gmail.com").password("password3").role("USER").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John4").lastname("Doe4").email("john4.doe@gmail.com").password("password4").role("ADMIN").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John5").lastname("Doe5").email("john5.doe@gmail.com").password("password5").role("USER").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John6").lastname("Doe6").email("john6.doe@gmail.com").password("password6").role("ADMIN").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John7").lastname("Doe7").email("john7.doe@gmail.com").password("password7").role("USER").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John8").lastname("Doe8").email("john8.doe@gmail.com").password("password8").role("USER").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John9").lastname("Doe9").email("john9.doe@gmail.com").password("password9").role("ADMIN").enabled(true).created(new Date()).build(),
                    User.builder().firstname("John10").lastname("Doe10").email("john10.doe@gmail.com").password("password10").role("USER").enabled(true).created(new Date()).build()
            );

            // Enregistrer tous les utilisateurs dans la base de données
            userRepository.saveAll(users);
        };
    }
}
