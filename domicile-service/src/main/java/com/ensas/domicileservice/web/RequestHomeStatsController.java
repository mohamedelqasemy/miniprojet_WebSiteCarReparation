package com.ensas.domicileservice.web;

import com.ensas.domicileservice.dtos.ChartPointDto;
import com.ensas.domicileservice.services.RequestHomeStatsService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@AllArgsConstructor
public class RequestHomeStatsController {
    private final RequestHomeStatsService requestHomeStatsService;

    @PreAuthorize("hasAuthority('INTERNAL')")
    @GetMapping("/demandes-domicile-par-mois")
    public List<ChartPointDto> getDemandesDomicileParMois() {
        return requestHomeStatsService.getDemandesDomicileParMois();
    }

}
