package com.ensas.domicileservice.dtos;

import com.ensas.domicileservice.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestHomeDto {
    private Long id;
    private Long userId;
    private User user;
    private String serviceType;
    private String address;
    private Date dateDemand;
    private String status;
}
