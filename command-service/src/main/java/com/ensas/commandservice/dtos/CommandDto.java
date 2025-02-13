package com.ensas.commandservice.dtos;

import com.ensas.commandservice.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommandDto {
    private Long id;
    private Date date;
    private int total;
    private EnumStatus status;
    private Long userId;
}
