package com.ensas.commandservice.services;

import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.mappers.CommandMapper;
import com.ensas.commandservice.repositories.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class CommandService {
    private final CommandRepository commandRepository;
    private final CommandDetailsService commandDetailsService;

    public CommandDto createCommand(CommandDto commandDto) {
        Command newCommand = CommandMapper.toEntity(commandDto);


        newCommand = commandRepository.save(newCommand);

        return CommandMapper.toDTO(newCommand);
    }
    public List<CommandDto> getAllCommands() {
        List<Command> commandList = (List<Command>) commandRepository.findAll();
        return CommandMapper.toDTOList(commandList);
    }

}
