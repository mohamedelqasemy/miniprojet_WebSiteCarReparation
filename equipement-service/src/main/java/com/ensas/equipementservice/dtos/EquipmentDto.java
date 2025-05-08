package com.ensas.equipementservice.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String type;
    private String car;
    private Long quantity;
    private List<String> imageLinks;
    private Double averageRating; //Moyenne des notes
    private List<String> comments; //Textes des commentaires

}
