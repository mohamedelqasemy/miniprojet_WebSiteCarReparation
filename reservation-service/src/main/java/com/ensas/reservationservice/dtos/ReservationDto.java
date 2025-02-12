package com.ensas.reservationservice.dtos;

import com.ensas.reservationservice.enums.EnumStatus;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private Long id;
    private Date date;
    private EnumStatus status;
    private Long carId;
    private Long userId;
    private Long serviceId;
}
