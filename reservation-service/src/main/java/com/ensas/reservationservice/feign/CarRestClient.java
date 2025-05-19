package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.model.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("car-service")
public interface CarRestClient {
    @PostMapping("/cars")
    CarDto createCar(@RequestBody CarDto carDto);
    @GetMapping("/cars/id-by-license")
    Long getCarIdByLicensePlate(@RequestParam String licensePlate);

}
