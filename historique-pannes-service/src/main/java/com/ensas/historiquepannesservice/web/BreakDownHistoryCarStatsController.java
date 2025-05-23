package com.ensas.historiquepannesservice.web;

import com.ensas.historiquepannesservice.dtos.ChartPointDto;
import com.ensas.historiquepannesservice.services.BreakDownHistoryStatsService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasAuthority('INTERNAL')")
@RequestMapping("/stats")
public class BreakDownHistoryCarStatsController {
    private final BreakDownHistoryStatsService breakDownHistoryStatsService;

    @GetMapping("/voitures-reparees-par-mois")
    public List<ChartPointDto> getCarReparationsParMois(){
        return breakDownHistoryStatsService.getCarReparationsParMois();
    }

}
