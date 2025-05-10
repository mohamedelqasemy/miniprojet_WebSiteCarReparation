package com.ensas.domicileservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestHomeRequest {
    private String brand;
    private String model;
    private String motorisation;
    private Float kilometers;
    private String address;
    private Long userId;
    private List<Long> serviceId;
    private Date date;
}
