package com.ensas.domicileservice.models;

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
