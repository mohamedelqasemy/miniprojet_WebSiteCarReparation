package com.ensas.equipementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserFeignClient {

    @GetMapping("/users/{id}/exists")
    Boolean checkUserExists(@PathVariable("id") Long id);
}