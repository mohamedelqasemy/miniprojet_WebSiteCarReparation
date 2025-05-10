package com.ensas.domicileservice.dtos;

import com.ensas.domicileservice.enums.EnumStatus;
import com.ensas.domicileservice.models.ServiceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestHomeResponse {
    private Long id;
    private String date;
    private EnumStatus status;
    private List<ServiceDto> services;

    private String client;
    private String address;
}