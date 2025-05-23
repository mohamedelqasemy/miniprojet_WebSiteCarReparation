package com.ensas.domicileservice.feign;

import com.ensas.domicileservice.config.FeignClientConfig;
import com.ensas.domicileservice.models.BreakdownHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "historique-pannes-service", configuration = FeignClientConfig.class)
public interface HistoryRestClient {
    @PostMapping("/histories")
    BreakdownHistoryDto createBreakdownHistory(@RequestBody BreakdownHistoryDto historyDto);
}
