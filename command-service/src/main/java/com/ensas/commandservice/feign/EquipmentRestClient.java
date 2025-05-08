package com.ensas.commandservice.feign;

import com.ensas.commandservice.models.Equipment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("equipement-service")
public interface EquipmentRestClient {
    @GetMapping("/equipments/{id}")
    Equipment getEquipmentById(@PathVariable Long id);

    @GetMapping("/equipments")
    PagedModel<Equipment> getAllEquipments();
}
