package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.model.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("car-service")
public interface CarRestClient {
    @PostMapping("/cars")
    CarDto createCar(@RequestBody CarDto carDto);
}
