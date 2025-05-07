package com.ensas.userservice.web;

import com.ensas.userservice.dtos.UserDto;
import com.ensas.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/users")
@AllArgsConstructor()
public class UserRestController {
    private UserService userService;

    //create a user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto user=userService.createUser(userDto);
        return ResponseEntity.ok(user);
    }

    //get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
       List<UserDto> userDtoList = userService.getAllUsers();
       return ResponseEntity.ok(userDtoList);
    }

    //get a specific user
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    //update some one
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id,@RequestBody UserDto userDto) {
       UserDto user = userService.updateUser(id,userDto);
       return ResponseEntity.ok(user);
    }

    //delete some one
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/exists")
    public Boolean checkUserExists(@PathVariable Long id) {
        System.out.println("==== checkUserExists ====");
        return userService.existsById(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserDto>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<UserDto> users = userService.getAllUsersPaginated(page, size);
        return ResponseEntity.ok(users);
    }


    @PostMapping("/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable final Long id, @RequestPart final MultipartFile file) {
        String url = userService.uploadImage(id, file);
        return ResponseEntity.ok(url);
    }
}

















