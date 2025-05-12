package com.ensas.commandservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartPointDto {
    private String mois;
    private int valeur;
}