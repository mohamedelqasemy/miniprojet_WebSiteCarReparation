package com.ensas.commandservice.mappers;

import com.ensas.commandservice.dtos.CommandDetailsDto;
import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.entities.CommandDetails;


import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CommandMapper {

    public static CommandDto toDTO(Command command) {
        if (command == null) return null;

        List<CommandDetailsDto> details = command.getCommandDetails() != null
                ? command.getCommandDetails().stream()
                .map(CommandMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return CommandDto.builder()
                .id(command.getId())
                .date(command.getDate())
                .total(command.getTotal())
                .status(command.getStatus())
                .userId(command.getUserId())
                .commandDetails(details)
                .build();
    }

    public static Command toEntity(CommandDto dto) {
        if (dto == null) return null;

        Command command = Command.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .total(dto.getTotal())
                .status(dto.getStatus())
                .userId(dto.getUserId())
                .build();

        if (dto.getCommandDetails() != null) {
            List<CommandDetails> details = dto.getCommandDetails().stream()
                    .map(detailDto -> toEntity(detailDto, command))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            command.setCommandDetails(details);
        }

        return command;
    }

    public static CommandDetailsDto toDTO(CommandDetails details) {
        if (details == null) return null;

        return CommandDetailsDto.builder()
                .id(details.getId())
                .commandId(details.getCommand() != null ? details.getCommand().getId() : null)
                .equipmentId(details.getEquipmentId())
                .quantity(details.getQuantity())
                .unitPrice(details.getUnitPrice())
                .build();
    }

    public static CommandDetails toEntity(CommandDetailsDto dto, Command command) {
        if (dto == null) return null;

        return CommandDetails.builder()
                .id(dto.getId())
                .command(command)
                .equipmentId(dto.getEquipmentId())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .build();
    }

    public static List<CommandDto> toDTOList(List<Command> entities) {
        if (entities == null || entities.isEmpty()) return Collections.emptyList();

        return entities.stream()
                .map(CommandMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<Command> toEntityList(List<CommandDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return Collections.emptyList();

        return dtos.stream()
                .map(CommandMapper::toEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
