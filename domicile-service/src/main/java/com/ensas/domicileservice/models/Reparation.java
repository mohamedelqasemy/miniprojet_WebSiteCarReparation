package com.ensas.domicileservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reparation {
    private Long id;
    private String name;
    private String description;
    private double servicePrice;
    private String image;
}
