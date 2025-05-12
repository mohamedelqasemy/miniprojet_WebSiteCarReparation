package com.ensas.historiquepannesservice.services;

import com.ensas.historiquepannesservice.dtos.ChartPointDto;
import com.ensas.historiquepannesservice.repositories.BreakdownHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BreakDownHistoryStatsService {
    private final BreakdownHistoryRepository breakdownHistoryRepository;

    public List<ChartPointDto> getCarReparationsParMois() {
        return breakdownHistoryRepository.findByIsRepairedTrue().stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDatePanne().getMonth()+1,
                        Collectors.summingInt(r -> 1)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Month month = Month.of(entry.getKey());
                    return new ChartPointDto(month.name(), entry.getValue());
                })
                .sorted(Comparator.comparing(dto -> Month.valueOf(dto.getMois())))
                .collect(Collectors.toList());
    }

}
