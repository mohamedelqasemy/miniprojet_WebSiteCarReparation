package com.ensas.paiementservice.entities;

import com.ensas.paiementservice.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Type;
    private Date Date;
    private Double Amount;

    //relation with user
    private Long userId;
    @Transient
    private User user;

}
