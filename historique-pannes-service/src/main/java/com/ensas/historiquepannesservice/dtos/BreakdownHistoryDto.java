package com.ensas.historiquepannesservice.dtos;

import com.ensas.historiquepannesservice.models.Car;
import com.ensas.historiquepannesservice.models.CarDto;
import com.ensas.historiquepannesservice.models.User;
import com.ensas.historiquepannesservice.models.UserDto;
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
    private String nomPanne;
    private String description;
    private Boolean isRepaired;

    //relation with car
    private Long carId;
    private CarDto car;


    //relation with user
    private Long userId;
    private UserDto user;

}
