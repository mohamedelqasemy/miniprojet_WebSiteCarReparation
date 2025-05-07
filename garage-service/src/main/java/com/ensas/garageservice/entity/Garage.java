package com.ensas.garageservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder @AllArgsConstructor @NoArgsConstructor
@ToString
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
