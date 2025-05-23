package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.config.FeignClientConfig;
import com.ensas.reservationservice.model.GarageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "garage-service",configuration = FeignClientConfig.class)
public interface GarageRestClient {
    @GetMapping("garages/{garageId}")
    GarageDto getGarageById(@PathVariable Long garageId);
}
