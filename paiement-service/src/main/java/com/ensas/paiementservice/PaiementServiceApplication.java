package com.ensas.paiementservice;

import com.ensas.paiementservice.entities.Payment;
import com.ensas.paiementservice.feign.UserRestClient;
import com.ensas.paiementservice.models.User;
import com.ensas.paiementservice.models.UserDto;
import com.ensas.paiementservice.repositories.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class PaiementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaiementServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(PaymentRepository paymentRepository,
//                                        UserRestClient userRestClient){
//
//        return args -> {
//            Collection<UserDto> users = userRestClient.getAllUsers().getContent();
//
//
//            users.forEach(user -> {
//                Payment payment = Payment.builder()
//                        .Type("carte")
//                        .Amount(100.00)
//                        .Date(new Date())
//                        .userId(user.getId())
//                        .build();
//                paymentRepository.save(payment);
//            });
//        };
//    }

}
