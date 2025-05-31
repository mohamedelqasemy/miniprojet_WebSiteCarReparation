package com.ensas.reservationservice;

import com.ensas.reservationservice.Repositories.ReservationRepository;
import com.ensas.reservationservice.dtos.ReservationDto;
import com.ensas.reservationservice.dtos.ReservationRequest;
import com.ensas.reservationservice.entities.Reservation;
import com.ensas.reservationservice.enums.EnumStatus;
import com.ensas.reservationservice.services.ReservationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(ReservationService reservationService) {
//        return args -> {
//            List<ReservationRequest> reservationRequests = List.of(
//                    new ReservationRequest("Toyota", "Yaris", "Essence", 120000f,"d42234", 1L, 1L, List.of(1L, 2L), new Date()),
//                    new ReservationRequest("Peugeot", "208", "Diesel", 80000f,"d42234", 1L, 2L, List.of(2L), new Date()),
//                    new ReservationRequest("Renault", "Clio", "Hybride", 50000f,"d42234", 1L, 3L, List.of(3L, 4L), new Date()),
//                    new ReservationRequest("Dacia", "Logan", "Essence", 60000f,"d42234", 1L, 4L, List.of(4L), new Date()),
//                    new ReservationRequest("Hyundai", "i10", "Diesel", 75000f,"d42234", 1L, 2L, List.of(5L), new Date()),
//                    new ReservationRequest("Ford", "Fiesta", "Essence", 90000f,"d42234", 1L, 4L, List.of(6L), new Date())
//            );
//
//
//            reservationRequests.forEach(dto -> {
//                try {
//                    Reservation created = reservationService.createReservation(dto);
//                    System.out.println("Réservation créée avec ID : " + created.getId());
//                } catch (Exception e) {
//                    System.err.println("Erreur lors de la création de réservation : " + e.getMessage());
//                }
//            });
//        };
//    }

}
