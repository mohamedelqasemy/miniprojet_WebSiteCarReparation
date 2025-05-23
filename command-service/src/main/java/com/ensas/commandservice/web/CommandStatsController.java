package com.ensas.commandservice.web;

import com.ensas.commandservice.dtos.ChartPointDto;
import com.ensas.commandservice.services.CommandService;
import com.ensas.commandservice.services.CommandeServiceStats;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('INTERNAL')")
public class CommandStatsController {

    private final CommandeServiceStats commandeServiceStats;

    @GetMapping("/total-commandes")
    public int getTotalCommandes() {
        return commandeServiceStats.countAllCommandes();
    }

    @GetMapping("/total-commandes-livred")
    public int getTotalCommandesLivred() {
        return commandeServiceStats.countCommandesLivred();
    }

    @GetMapping("/total-commandes-cancled")
    public int getTotalCommandesCancled() {
        return commandeServiceStats.countCommandesAnnulees();
    }

    @GetMapping("/total-commandes-profit")
    public double getTotalProfit() {
        return commandeServiceStats.calculerProfitTotal();
    }

    @GetMapping("/commandes-par-mois")
    public List<ChartPointDto> getCommandesParMois() {
        return commandeServiceStats.getStatistiquesParMois();
    }
}