package com.ensas.domicileservice.services;

import com.ensas.domicileservice.dtos.ChartPointDto;
import com.ensas.domicileservice.entities.RequestHome;
import com.ensas.domicileservice.repositories.RequestHomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestHomeStatsService {

    private final RequestHomeRepository requestHomeRepository;


    public List<ChartPointDto> getDemandesDomicileParMois() {
        return requestHomeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        rh -> rh.getDateDemand().getMonth()+1,
                        Collectors.summingInt(rh -> 1)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Month month = Month.of(entry.getKey());
                    return new ChartPointDto(month.name(), entry.getValue());
                })
                .sorted(Comparator.comparing(a -> Month.valueOf(a.getMois())))
                .collect(Collectors.toList());
    }

}
