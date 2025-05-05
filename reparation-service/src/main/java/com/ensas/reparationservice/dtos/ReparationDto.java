package com.ensas.reparationservice.dtos;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReparationDto {
    private Long id;
    private String name;
    private String description;
    private String type;
    private Date dateOfCreation;
    private Double servicePrice;
    private String image; // Stockera juste le nom ou le chemin de l’image
}
