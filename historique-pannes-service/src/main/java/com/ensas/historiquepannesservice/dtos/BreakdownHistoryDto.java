package com.ensas.historiquepannesservice.dtos;

import com.ensas.historiquepannesservice.models.Car;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
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
    private String description;
    private Boolean isRepaired;

    //relation with car
    private Long carId;
    private Car car;
}
