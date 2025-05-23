package com.ensas.domicileservice.feign;

import com.ensas.domicileservice.config.FeignClientConfig;
import com.ensas.domicileservice.models.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "car-service", configuration = FeignClientConfig.class)
public interface CarRestClient {
    @PostMapping("/cars")
    CarDto createCar(@RequestBody CarDto carDto);
    @GetMapping("/cars/id-by-license")
    Long getCarIdByLicensePlate(@RequestParam String licensePlate);

}
