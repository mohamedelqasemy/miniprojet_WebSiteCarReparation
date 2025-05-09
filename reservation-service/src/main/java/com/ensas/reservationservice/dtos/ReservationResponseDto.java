package com.ensas.reservationservice.dtos;

import com.ensas.reservationservice.enums.EnumStatus;
import com.ensas.reservationservice.model.ServiceDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationResponseDto {
    private Long id;
    private String date;
    private EnumStatus status;

    private List<ServiceDto> services;

    //to be deleted after
    private String client;
    private String adresse;
    private String name; // nom du service et placer pour cette reservation
    private Double prix;
    private String typeService;
    private String image;
}