package com.ensas.commandservice.dtos;

import com.ensas.commandservice.enums.EnumStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CommandDto {
    private Long id;
    private Date date;
    private int total;
    @Enumerated(EnumType.STRING)
    private EnumStatus status;
    private Long userId;
    private List<CommandDetailsDto> commandDetails;
}
