package com.ensas.reservationservice.feign;

import com.ensas.reservationservice.config.FeignClientConfig;
import com.ensas.reservationservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",configuration = FeignClientConfig.class)
public interface UserRestClient {
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id);

    @GetMapping("/users")
    PagedModel<User> getAllUsers();
}
