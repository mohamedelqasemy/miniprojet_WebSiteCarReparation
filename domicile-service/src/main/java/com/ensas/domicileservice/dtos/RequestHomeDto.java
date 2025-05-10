package com.ensas.domicileservice.dtos;

import com.ensas.domicileservice.enums.EnumStatus;
import com.ensas.domicileservice.models.UserDto;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestHomeDto {
    private Long id;
    private Long userId;
    private UserDto user;
    private String address;
    private Date dateDemand;
    private EnumStatus status;
}
