package com.ensas.userservice.web;

import com.ensas.userservice.dtos.UserDto;
import com.ensas.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@AllArgsConstructor()
public class UserRestController {
    private UserService userSerivce;

    //create a user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto user=userSerivce.createUser(userDto);
        return ResponseEntity.ok(user);
    }

    //get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
       List<UserDto> userDtoList = userSerivce.getAllUsers();
       return ResponseEntity.ok(userDtoList);
    }

    //get a specific user
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userSerivce.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    //update some one
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,@RequestBody UserDto userDto) {
       UserDto user = userSerivce.updateUser(id,userDto);
       return ResponseEntity.ok(user);
    }

    //delete some one
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userSerivce.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}

















