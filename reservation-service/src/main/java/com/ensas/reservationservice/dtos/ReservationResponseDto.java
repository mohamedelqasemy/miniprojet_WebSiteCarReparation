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
    private Long carId;
    private Long garageId;

    private List<ServiceDto> services;
    private String client;
}