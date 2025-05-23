package com.ensas.dashboardservice.feign;

import com.ensas.dashboardservice.config.FeignClientConfig;
import com.ensas.dashboardservice.dto.ChartPointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "command-service", configuration = FeignClientConfig.class)
public interface CommandeClient {
    @GetMapping("/stats/total-commandes")
    int getTotalCommandes();
    @GetMapping("/stats/total-commandes-livred")
    int getTotalCommandesLivred();
    @GetMapping("/stats/total-commandes-cancled")
    int getTotalCommandesCancled();
    @GetMapping("/stats/total-commandes-profit")
    double getTotalProfit();

    @GetMapping("/stats/commandes-par-mois")
    List<ChartPointDto> getCommandesParMois();
}