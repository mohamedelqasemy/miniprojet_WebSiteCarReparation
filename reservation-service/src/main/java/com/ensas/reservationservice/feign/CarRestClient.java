package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.config.FeignClientConfig;
import com.ensas.reservationservice.model.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "car-service", configuration = FeignClientConfig.class)
public interface CarRestClient {
    @PostMapping("/cars")
    CarDto createCar(@RequestBody CarDto carDto);
    @GetMapping("/cars/id-by-license")
    Long getCarIdByLicensePlate(@RequestParam String licensePlate);
    @DeleteMapping("/cars/{id}")
    void deleteCar(@PathVariable Long id);
}
