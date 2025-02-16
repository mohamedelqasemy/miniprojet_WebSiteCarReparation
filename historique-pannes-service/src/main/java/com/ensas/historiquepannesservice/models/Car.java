package com.ensas.historiquepannesservice.models;

import lombok.*;

import java.util.Date;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private Long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String image;
    private Date productionYear;
    private Long userId;
}
