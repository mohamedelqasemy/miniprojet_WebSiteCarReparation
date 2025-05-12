package com.ensas.dashboardservice.feign;

import com.ensas.dashboardservice.dto.ChartPointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "domicile-service")
public interface DomicileClient {
    @GetMapping("/stats/demandes-domicile-par-mois")
    List<ChartPointDto> getDemandesParMois();
}
