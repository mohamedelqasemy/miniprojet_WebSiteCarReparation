package com.ensas.commandservice.services;

import com.ensas.commandservice.dtos.CommandDetailsDto;
import com.ensas.commandservice.dtos.CommandDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.entities.CommandDetails;
import com.ensas.commandservice.feign.EquipmentRestClient;
import com.ensas.commandservice.mappers.CommandMapper;
import com.ensas.commandservice.models.Equipment;
import com.ensas.commandservice.repositories.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final CommandRepository commandRepository;
    private final EquipmentRestClient equipmentRestClient;

    // Créer une commande
    public CommandDto createCommand(CommandDto commandDto) {
        // Convert DTO to entity
        Command newCommand = CommandMapper.toEntity(commandDto);

        // Définir la date de création
        newCommand.setDate(new Date());

        // Associer chaque CommandDetail à la commande AVANT sauvegarde
        if (newCommand.getCommandDetails() != null) {
            for (CommandDetails detail : newCommand.getCommandDetails()) {
                detail.setCommand(newCommand);
            }
        }

        // Sauvegarde de la commande avec ses détails (si CascadeType.ALL est bien configuré)
        Command savedCommand = commandRepository.save(newCommand);

        // Retourner le DTO enrichi
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

    public Page<CommandDto> getCommandsByUserIdPaginated(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Command> commandPage = commandRepository.findByUserId(userId, pageable);

        // Collecter tous les equipmentIds utilisés dans les commandes
        Set<Long> equipmentIds = commandPage.getContent().stream()
                .flatMap(command -> command.getCommandDetails().stream())
                .map(CommandDetails::getEquipmentId)
                .collect(Collectors.toSet());

        // Récupération en une seule fois
        List<Equipment> equipments = equipmentRestClient.getEquipmentsByIds(new ArrayList<>(equipmentIds));
        Map<Long, Equipment> equipmentMap = equipments.stream()
                .collect(Collectors.toMap(Equipment::getId, e -> e));

        // Mapper les commandes enrichies
        return commandPage.map(command -> {
            List<CommandDetailsDto> enrichedDetails = command.getCommandDetails().stream().map(detail -> {
                Equipment equipment = equipmentMap.get(detail.getEquipmentId());

                return CommandDetailsDto.builder()
                        .id(detail.getId())
                        .commandId(detail.getCommand().getId())
                        .equipmentId(detail.getEquipmentId())
                        .quantity(detail.getQuantity())
                        .unitPrice(detail.getUnitPrice())
                        .equipmentName(equipment != null ? equipment.getName() : null)
                        .equipmentImage((equipment != null && equipment.getImageLinks() != null && !equipment.getImageLinks().isEmpty())
                                ? equipment.getImageLinks().get(0)
                                : null)
                        .build();
            }).collect(Collectors.toList());

            return CommandDto.builder()
                    .id(command.getId())
                    .date(command.getDate())
                    .status(command.getStatus())
                    .total(command.getTotal())
                    .userId(command.getUserId())
                    .commandDetails(enrichedDetails)
                    .build();
        });
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
