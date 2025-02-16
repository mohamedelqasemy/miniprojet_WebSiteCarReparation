package com.ensas.commandservice.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommandDetailsDto {
    private Long id;
    private Long commandId;
    private Long equipmentId;
    private int quantity;
    private int unitPrice;
}
