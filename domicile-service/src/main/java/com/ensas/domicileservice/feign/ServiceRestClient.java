package com.ensas.domicileservice.feign;

import com.ensas.domicileservice.models.Reparation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("reparation-service")
public interface ServiceRestClient {
    @GetMapping("/reparations/{id}")
    Reparation getServiceById(@PathVariable Long id);

    @GetMapping("/reparations")
    PagedModel<Reparation> getAllServices();
}
