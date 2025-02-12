package com.ensas.reservationservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Service {
    private Long id;
    private String name;
    private String description;
    private double price;
}
