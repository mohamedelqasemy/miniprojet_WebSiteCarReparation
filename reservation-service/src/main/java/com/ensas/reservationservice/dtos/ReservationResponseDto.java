package com.ensas.reservationservice.dtos;

import com.ensas.reservationservice.enums.EnumStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationResponseDto {
    private Long id;
    private String date;
    private EnumStatus status;

    private String client;
    private String adresse;
    private String name; // nom du service et placer pour cette reservation
    private Double prix;
    private String typeService;
    private String image;
}