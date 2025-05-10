package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.model.GarageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("garage-service")
public interface GarageRestClient {
    @GetMapping("garages/{garageId}")
    GarageDto getGarageById(@PathVariable Long garageId);
}
