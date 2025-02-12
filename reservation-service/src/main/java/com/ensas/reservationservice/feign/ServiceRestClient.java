package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.model.Service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("reparation-service")
public interface ServiceRestClient {
    @GetMapping("/reparations/{id}")
    Service getServiceById(@PathVariable Long id);

    @GetMapping("/reparations")
    PagedModel<Service> getAllServices();
}
