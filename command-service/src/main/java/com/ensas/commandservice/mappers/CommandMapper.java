package com.ensas.commandservice.mappers;

import com.ensas.commandservice.dtos.CommandDetailsDto;
import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.entities.CommandDetails;

import java.util.List;
import java.util.stream.Collectors;

public class CommandMapper {

    // Convertir une entité Command en DTO
    public static CommandDto toDTO(Command command) {
        if (command == null) {
            return null;
        }
        return new CommandDto(
                command.getId(),
                command.getDate(),
                command.getTotal(),
                command.getStatus(),
                command.getUserId(),
                command.getCommandDetails() != null ?
                        command.getCommandDetails().stream().map(CommandMapper::toDTO).collect(Collectors.toList())
                        : null
        );
    }

    // Convertir un DTO en entité Command
    public static Command toEntity(CommandDto commandDto) {
        if (commandDto == null) {
            return null;
        }
        Command command = new Command(
                commandDto.getId(),
                commandDto.getDate(),
                commandDto.getTotal(),
                commandDto.getStatus(),
                commandDto.getUserId(),
                null
        );

        if (commandDto.getCommandDetails() != null) {
            command.setCommandDetails(commandDto.getCommandDetails().stream()
                    .map(detailsDto -> toEntity(detailsDto, command))
                    .collect(Collectors.toList()));
        }

        return command;
    }

    // Convertir une entité CommandDetails en DTO
    public static CommandDetailsDto toDTO(CommandDetails commandDetails) {
        if (commandDetails == null) {
            return null;
        }
        return new CommandDetailsDto(
                commandDetails.getId(),
                commandDetails.getCommand().getId(), // Utilisation de l'ID au lieu de l'objet complet
                commandDetails.getEquipmentId(),
                commandDetails.getQuantity(),
                commandDetails.getUnitPrice()
        );
    }

    // Convertir un DTO en entité CommandDetails
    public static CommandDetails toEntity(CommandDetailsDto commandDetailsDto, Command command) {
        if (commandDetailsDto == null) {
            return null;
        }
        return new CommandDetails(
                commandDetailsDto.getId(),
                command,
                commandDetailsDto.getEquipmentId(),
                commandDetailsDto.getQuantity(),
                commandDetailsDto.getUnitPrice()
        );
    }

    // Convertir une liste de Command en liste de CommandDto
    public static List<CommandDto> toDTOList(List<Command> commandList) {
        return commandList.stream()
                .map(CommandMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Convertir une liste de CommandDto en liste de Command
    public static List<Command> toEntityList(List<CommandDto> commandDtoList) {
        return commandDtoList.stream()
                .map(CommandMapper::toEntity)
                .collect(Collectors.toList());
    }
}
