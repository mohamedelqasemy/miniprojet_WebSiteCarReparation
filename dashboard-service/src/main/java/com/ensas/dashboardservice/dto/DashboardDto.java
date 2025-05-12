package com.ensas.dashboardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DashboardDto {
    private int totalCommandes;
    private int totalCommandesLivred;
    private int totalCommandesCancled;
    private double totalProfit;
    private List<ChartPointDto> commandesParMois;
    private List<ChartPointDto> voituresRepareesParMois;
    private List<ChartPointDto> demandesDomicileParMois;
}