package com.ensas.paiementservice.feign;

import com.ensas.paiementservice.models.User;
import com.ensas.paiementservice.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "user-service")
public interface UserRestClient {
    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/users")
    PagedModel<UserDto> getAllUsers();
}


