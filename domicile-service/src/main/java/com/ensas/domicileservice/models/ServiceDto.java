package com.ensas.domicileservice.models;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDto {
    private String name;
    private Double price;
    private String image;
    private String typeService;
}
