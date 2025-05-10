package com.ensas.domicileservice.entities;

import com.ensas.domicileservice.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestHome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long carId;
    @ElementCollection
    private List<Long> serviceId;
    private String address;
    private Date dateDemand;
    private EnumStatus status;
}
