package com.ensas.carservice.clients;

import com.ensas.carservice.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "user-service")
public interface UserRestClient {
    @GetMapping("/users/{id}")
    User findUserById(@PathVariable Long id);
    @GetMapping("/users")
    List<User> findAllUsers();
}
