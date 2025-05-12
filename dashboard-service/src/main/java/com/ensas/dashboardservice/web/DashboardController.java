package com.ensas.dashboardservice.web;

import com.ensas.dashboardservice.dto.DashboardDto;
import com.ensas.dashboardservice.feign.CommandeClient;
import com.ensas.dashboardservice.feign.DomicileClient;
import com.ensas.dashboardservice.feign.CarClientRepaired;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final CommandeClient commandeClient;
    private final CarClientRepaired carClientRepaired;
    private final DomicileClient domicileClient;


    @GetMapping
    public DashboardDto getDashboard() {
        return new DashboardDto(
                commandeClient.getTotalCommandes(),
                commandeClient.getTotalCommandesLivred(),
                commandeClient.getTotalCommandesCancled(),
                commandeClient.getTotalProfit(),
                commandeClient.getCommandesParMois(),
                carClientRepaired.getReparationsParMois(),
                domicileClient.getDemandesParMois()
        );
    }
}
