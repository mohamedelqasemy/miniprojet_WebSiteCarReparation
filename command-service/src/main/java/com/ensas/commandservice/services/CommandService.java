package com.ensas.commandservice.services;

import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.mappers.CommandMapper;
import com.ensas.commandservice.repositories.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommandService {
    private final CommandRepository commandRepository;

    //creat a command
    public CommandDto createCommand(CommandDto commandDto) {
        Command newCommand = CommandMapper.toEntity(commandDto);
        Command savedCommand = commandRepository.save(newCommand);
        return CommandMapper.toDTO(savedCommand);
    }

    //retrieve all commands
    public List<CommandDto> getAllCommands() {
        List<Command> commandList = (List<Command>) commandRepository.findAll();
        return CommandMapper.toDTOList(commandList);
    }

    //get a specific command
    public CommandDto getCommandById(Long id) {
        return commandRepository.findById(id)
                .map(CommandMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("commande non trouvé"));
    }

    //update a specific command
    public CommandDto updateCommand(Long id, CommandDto commandDto) {
        Command command = commandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Mise à jour des champs principaux
        if (commandDto.getStatus() != null) {
            command.setStatus(commandDto.getStatus());
        }
        if (commandDto.getTotal() != command.getTotal()) {
            command.setTotal(commandDto.getTotal());
        }
        if (commandDto.getDate() != null) {
            command.setDate(commandDto.getDate());
        }
        if (commandDto.getUserId() != null) {
            command.setUserId(commandDto.getUserId());
        }

        // Mise à jour des détails de la commande
        if (commandDto.getCommandDetails() != null) {
            command.getCommandDetails().clear();
            commandDto.getCommandDetails().forEach(detailsDto -> {
                command.getCommandDetails().add(CommandMapper.toEntity(detailsDto, command));
            });
        }

        // Sauvegarder la commande mise à jour
        Command updatedCommand = commandRepository.save(command);
        return CommandMapper.toDTO(updatedCommand);
    }


    //delete a specific command
    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }






}
