package com.ensas.paiementservice.dtos;

import com.ensas.paiementservice.models.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDto {
    private Long id;
    private String Type;
    private Date Date;
    private Double Amount;

    //relation with user
    private Long userId;
    private User user;
}
