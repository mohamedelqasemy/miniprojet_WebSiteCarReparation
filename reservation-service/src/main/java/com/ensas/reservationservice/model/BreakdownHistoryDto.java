package com.ensas.reservationservice.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BreakdownHistoryDto {
    private Long id;
    private Date datePanne;
    private String nomPanne;
    private String description;
    private Boolean isRepaired;

    //relation with car
    private Long carId;
    private CarDto car;
}
