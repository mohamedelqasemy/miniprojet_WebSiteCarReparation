package com.ensas.reservationservice.model;

import lombok.*;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDto {
    private String name;
    private Double price;
    private String image;
    private String typeService;
}
