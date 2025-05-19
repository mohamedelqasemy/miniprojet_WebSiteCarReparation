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
                            .publicId("atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658")
                            .image("https://res.cloudinary.com/dhfpiuaef/image/upload/v1746615425/atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658.jpg")
                            .address("Agadir avenue")
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
                            .publicId("atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658")
                            .image("https://res.cloudinary.com/dhfpiuaef/image/upload/v1746615425/atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658.jpg")
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
                            .publicId("atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658")
                            .image("https://res.cloudinary.com/dhfpiuaef/image/upload/v1746615425/atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658.jpg")
                            .address("Boulevard Al joulane")
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
                            .publicId("atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658")
                            .image("https://res.cloudinary.com/dhfpiuaef/image/upload/v1746615425/atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658.jpg")
                            .address("Avenue Mohammed 6")
                            .build(),
                    User.builder()
                            .firstname("cena")
                            .lastname("john")
                            .email("johncena@gmail.com")
                            .password("12345678")
                            .role("USER")
                            .num("0688453925")
                            .enabled(true)
                            .created(new Date())
                            .publicId("atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658")
                            .image("https://res.cloudinary.com/dhfpiuaef/image/upload/v1747514462/atlas/clients/atlas/clients/achraf_20250517214100.png")
                            .address("Avenue Hassan 2")
                            .build(),
                    User.builder()
                            .firstname("admin")
                            .lastname("admin")
                            .email("admin@gmail.com")
                            .password("admin1234")
                            .role("ADMIN")
                            .num("0688453925")
                            .enabled(true)
                            .created(new Date())
                            .publicId("atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658")
                            .image("https://res.cloudinary.com/dhfpiuaef/image/upload/v1746615425/atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658.jpg")
                            .address("Avenue Hassan 2")
                            .build(),
                    User.builder()
                            .firstname("super")
                            .lastname("admin")
                            .email("superadmin@gmail.com")
                            .password("superadmin")
                            .role("SUPER ADMIN")
                            .num("0688453925")
                            .enabled(true)
                            .created(new Date())
                            .publicId("atlas/clients/atlas/clients/Mohamed_Salah_2018_20250507115658")
                            .image("https://res.cloudinary.com/dhfpiuaef/image/upload/v1747654661/super_admin_c4ia9a.jpg")
                            .address("Avenue Hassan 2")
                            .build()
            );

            userRepository.saveAll(users);
        };
    }
}
