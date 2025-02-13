package com.ensas.reparationservice.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReparationDto {
    private Long id;
    private String name;
    private String description;
    private Double servicePrice;
}
