package com.ensas.domicileservice.clients;

import com.ensas.domicileservice.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserRestClient {
    @GetMapping("/users/{id}")
    UserDTO findUserById(@PathVariable("id") Long id);

    @GetMapping("/users")
    List<UserDTO> findAllUsers();
}
