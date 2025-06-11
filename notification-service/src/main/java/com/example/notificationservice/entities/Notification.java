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
public class Notification {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private Long userId;
    private String user;
    private Date date;
    private String licensePlate;
    private boolean seen;
}
