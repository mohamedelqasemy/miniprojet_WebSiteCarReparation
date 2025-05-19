package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.model.BreakdownHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("historique-pannes-service")
public interface HistoryRestClient {
    @PostMapping("/histories")
    BreakdownHistoryDto createBreakdownHistory(@RequestBody BreakdownHistoryDto historyDto);
}
