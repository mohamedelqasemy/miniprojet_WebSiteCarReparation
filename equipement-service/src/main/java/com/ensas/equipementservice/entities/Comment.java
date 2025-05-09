package com.ensas.equipementservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Long userId;
    private Date date;
    @ManyToOne
    private Equipment equipment;
}

