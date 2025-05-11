package com.ensas.carservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String image;
    private Float kilometers;
    private String motorisation;
    private Long userId;
}
