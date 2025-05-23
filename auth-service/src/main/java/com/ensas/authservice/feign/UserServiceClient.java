package com.ensas.authservice.feign;

import com.ensas.authservice.config.FeignClientConfig;
import com.ensas.authservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service",configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @PostMapping("/users")
    UserDto createUser(@RequestBody UserDto userDto);
    @GetMapping("/users/by-username/{email}")
    UserDto getUserByEmail(@PathVariable("email") String email);

}