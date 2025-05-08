package com.ensas.commandservice.services;

import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.feign.EquipmentRestClient;
import com.ensas.commandservice.mappers.CommandMapper;
import com.ensas.commandservice.models.Equipment;
import com.ensas.commandservice.repositories.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final CommandRepository commandRepository;
    private final EquipmentRestClient equipmentRestClient;

    // Créer une commande
    public CommandDto createCommand(CommandDto commandDto) {
        Command newCommand = CommandMapper.toEntity(commandDto);
        newCommand.setDate(new Date());
        Command savedCommand = commandRepository.save(newCommand);
        return enrichCommandDto(CommandMapper.toDTO(savedCommand));
    }

    // Récupérer toutes les commandes avec pagination
    public Page<CommandDto> getAllCommands(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Command> commandPage = commandRepository.findAll(pageRequest);
        return commandPage.map(command -> enrichCommandDto(CommandMapper.toDTO(command)));
    }

    // Récupérer une commande spécifique
    public CommandDto getCommandById(Long id) {
        return commandRepository.findById(id)
                .map(CommandMapper::toDTO)
                .map(this::enrichCommandDto)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }

    // Mettre à jour une commande
    public CommandDto updateCommand(Long id, CommandDto commandDto) {
        Command command = commandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        if (commandDto.getStatus() != null) command.setStatus(commandDto.getStatus());
        if (commandDto.getDate() != null) command.setDate(commandDto.getDate());
        if (commandDto.getUserId() != null) command.setUserId(commandDto.getUserId());

        if (commandDto.getCommandDetails() != null) {
            command.getCommandDetails().clear();
            commandDto.getCommandDetails().forEach(detailDto ->
                    command.getCommandDetails().add(CommandMapper.toEntity(detailDto, command))
            );
        }

        Command updatedCommand = commandRepository.save(command);
        return enrichCommandDto(CommandMapper.toDTO(updatedCommand));
    }

    // Supprimer une commande
    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }

    // Récupérer toutes les commandes d’un utilisateur
    public List<CommandDto> getCommandsByUserId(Long userId) {
        List<Command> commands = commandRepository.findByUserId(userId);
        return CommandMapper.toDTOList(commands).stream()
                .map(this::enrichCommandDto)
                .toList();
    }

    // Méthode privée pour enrichir un CommandDto avec les données d’équipement
    private CommandDto enrichCommandDto(CommandDto commandDto) {
        if (commandDto.getCommandDetails() != null) {
            commandDto.getCommandDetails().forEach(detail -> {
                try {
                    Equipment equipment = equipmentRestClient.getEquipmentById(detail.getEquipmentId());
                    if (equipment != null) {
                        detail.setEquipmentName(equipment.getName());
                        if (equipment.getImageLinks() != null && !equipment.getImageLinks().isEmpty()) {
                            detail.setEquipmentImage(equipment.getImageLinks().get(0));
                        }
                    }
                } catch (Exception e) {
                    detail.setEquipmentName("Unknown");
                }
            });
        }
        return commandDto;
    }
}
