package com.ensas.garageservice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GarageDto {
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private int note;
    private Date OuvertureDate;
    private Date FermetureDate;
    private Date dateCreation;
    private String image;
}
