package com.ensas.historiquepannesservice.entities;

import com.ensas.historiquepannesservice.models.Car;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BreakdownHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date datePanne;
    private String nomPanne;
    private String description;
    private Boolean isRepaired;

    //relation with car
    private Long carId;
    @Transient private Car car;



}
