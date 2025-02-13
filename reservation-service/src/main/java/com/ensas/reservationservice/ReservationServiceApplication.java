package com.ensas.reservationservice;

import com.ensas.reservationservice.Repositories.ReservationRepository;
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
    CommandLineRunner commandLineRunner(ReservationRepository reservationRepository) {
        return args -> {
            List<Reservation> reservations = List.of(
                    Reservation.builder()
                            .date(new Date())
                            .status(EnumStatus.Pending)
                            .serviceId(1L)
                            .carId(1L)
                            .userId(1L)
                            .build()
                    ,
                    Reservation.builder()
                            .date(new Date())
                            .status(EnumStatus.Pending)
                            .serviceId(1L)
                            .carId(1L)
                            .userId(2L)
                            .build()
            );
            reservationRepository.saveAll(reservations);
        };
    }

}
