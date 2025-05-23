package com.ensas.historiquepannesservice.feign;

import com.ensas.historiquepannesservice.config.FeignClientConfig;
import com.ensas.historiquepannesservice.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",configuration = FeignClientConfig.class)
public interface UserRestClient {
    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/users")
    PagedModel<UserDto> getAllUsers();
}


