package com.ensas.reservationservice.entities;

import com.ensas.reservationservice.enums.EnumStatus;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private EnumStatus status;
    private Long carId;
    private Long userId;
    private Long GarageId;
    @ElementCollection
    private List<Long> serviceId;
    @ElementCollection
    private List<String> serviceNames;

}
