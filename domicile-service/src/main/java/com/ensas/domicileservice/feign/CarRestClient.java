package com.ensas.domicileservice.feign;

import com.ensas.domicileservice.models.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("car-service")
public interface CarRestClient {
    @PostMapping("/api/cars")
    CarDto createCar(@RequestBody CarDto carDto);
}
