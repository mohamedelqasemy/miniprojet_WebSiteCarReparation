package com.ensas.dashboardservice.feign;

import com.ensas.dashboardservice.dto.ChartPointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "historique-pannes-service")
public interface CarClientRepaired {
    @GetMapping("/stats/voitures-reparees-par-mois")
    List<ChartPointDto> getReparationsParMois();
}