package com.ensas.equipementservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="equipments")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long quantity;

}
