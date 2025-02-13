package com.ensas.commandservice.mappers;

import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;

import java.util.List;
import java.util.stream.Collectors;

public class CommandMapper {

    public static CommandDto toDTO(Command command) {
        if (command == null) {
            return null;
        }
        return new CommandDto(command.getId(),command.getDate(),command.getTotal(),command.getStatus(),command.getUserId());
    }

    public static Command toEntity(CommandDto commandDto) {
        if (commandDto == null) {
            return null;
        }
        return new Command(commandDto.getId(),commandDto.getDate(),commandDto.getTotal(),commandDto.getStatus(),commandDto.getUserId());
    }

    public static List<CommandDto> toDTOList(List<Command> commandList) {
        return commandList.stream()
                .map(CommandMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Command> toEntityList(List<CommandDto> commandDtoList) {
        return commandDtoList.stream()
                .map(CommandMapper::toEntity)
                .collect(Collectors.toList());
    }



}
