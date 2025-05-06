package com.ensas.reservationservice;

import com.ensas.reservationservice.Repositories.ReservationRepository;
import com.ensas.reservationservice.dtos.ReservationDto;
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

    @Bean
    CommandLineRunner commandLineRunner(ReservationService reservationService) {
        return args -> {
            List<ReservationDto> reservationDtos = List.of(
                    new ReservationDto(null, new Date(), EnumStatus.Pending, 1L, 1L, 1L),
                    new ReservationDto(null, new Date(), EnumStatus.Pending, 2L, 2L, 2L),
                    new ReservationDto(null, new Date(), EnumStatus.Pending, 3L, 3L, 2L),
                    new ReservationDto(null, new Date(), EnumStatus.Pending, 4L, 4L, 2L),
                    new ReservationDto(null, new Date(), EnumStatus.Pending, 5L, 5L, 2L),
                    new ReservationDto(null, new Date(), EnumStatus.Pending, 6L, 6L, 2L)


            );

            reservationDtos.forEach(dto -> {
                try {
                    Reservation created = reservationService.createReservation(dto);
                    System.out.println("Réservation créée avec ID : " + created.getId());
                } catch (Exception e) {
                    System.err.println("Erreur lors de la création de réservation : " + e.getMessage());
                }
            });
        };
    }

}
