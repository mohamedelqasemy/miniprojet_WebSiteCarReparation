package com.ensas.historiquepannesservice.feign;



import com.ensas.historiquepannesservice.models.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "car-service")
public interface CarRestClient {
    @GetMapping("/cars/{id}")
    CarDto getCarById(@PathVariable("id") Long id);

    @GetMapping("/cars")
    PagedModel<CarDto> getAllCars();
}
