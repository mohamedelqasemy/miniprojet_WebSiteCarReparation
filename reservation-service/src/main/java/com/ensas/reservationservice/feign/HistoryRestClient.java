package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.model.BreakdownHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("historique-pannes-service")
public interface HistoryRestClient {
    @PostMapping("/histories")
    BreakdownHistoryDto createBreakdownHistory(@RequestBody BreakdownHistoryDto historyDto);

    @DeleteMapping("/histories/{id}")
    void deleteHistory(@PathVariable Long id);

    @GetMapping("/histories/by-car/{carId}")
    List<BreakdownHistoryDto> getHistoriesByCarId(@PathVariable Long carId);

}
