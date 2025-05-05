package com.ensas.equipementservice.dtos;

import jakarta.persistence.*;
import lombok.*;

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
    private Long quantity;
    private String image; // Le nom ou le chemin de l’image
}
