package com.ensas.commandservice.services;

import com.ensas.commandservice.dtos.ChartPointDto;
import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.enums.EnumStatus;
import com.ensas.commandservice.repositories.CommandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommandeServiceStats {
    private final CommandRepository commandRepository;

    public int countAllCommandes() {
        return (int) commandRepository.count();
    }

    public int countCommandesLivred() {
        return commandRepository.countByStatus(EnumStatus.LIVREE);
    }

    public int countCommandesAnnulees() {
        return commandRepository.countByStatus(EnumStatus.ANNULEE);
    }

    public double calculerProfitTotal() {
        List<Command> commandesLivrees = commandRepository.findByStatus(EnumStatus.LIVREE);
        return commandesLivrees.stream()
                .mapToDouble(Command::getTotal)
                .sum();
    }

    public List<ChartPointDto> getStatistiquesParMois() {
        List<Command> allCommands = commandRepository.findAll();

        return allCommands.stream()
                .collect(Collectors.groupingBy(
                        command -> command.getDate().getMonth()+1,
                        Collectors.summingInt(c -> 1)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Month month = Month.of(entry.getKey());
                    return new ChartPointDto(month.name(), entry.getValue());
                })
                .sorted((a, b) -> Month.valueOf(a.getMois()).compareTo(Month.valueOf(b.getMois())))
                .collect(Collectors.toList());
    }
}
