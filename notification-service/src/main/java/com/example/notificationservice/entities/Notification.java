package com.example.notificationservice.entities;

import jakarta.persistence.GeneratedValue;
import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // For PostgreSQL auto-increment
    private Long id;
    private String name;
    @Column(name = "user_id")
    private Long userId;
    private Date date;
    @Column(name = "license_plate")
    private String licensePlate;
    private boolean seen;
}
