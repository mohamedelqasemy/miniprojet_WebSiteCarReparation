package com.ensas.carservice.dtos;

import com.ensas.carservice.models.User;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarDto {
    private Long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String image;
    private Date productionYear;
    private Long userId;
    private User user;
}
