package com.ensas.domicileservice.feign;

import com.ensas.domicileservice.config.FeignClientConfig;
import com.ensas.domicileservice.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserRestClient {
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id);

    @GetMapping("/users")
    PagedModel<User> getAllUsers();
}
