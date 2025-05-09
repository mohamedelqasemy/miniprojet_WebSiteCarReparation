package com.ensas.reservationservice.dtos;

import com.ensas.reservationservice.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationRequest {
    private String brand;
    private String model;
    private String motorisation;
    private Float kilometers;
    private Long garageId;
    private Long userId;
    private List<Long> serviceId;
    private Date date;
}
