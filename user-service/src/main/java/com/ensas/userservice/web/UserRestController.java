package com.ensas.userservice.web;

import com.ensas.userservice.dtos.PasswordChangeRequest;
import com.ensas.userservice.dtos.UserDto;
import com.ensas.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/users")
@AllArgsConstructor()
public class UserRestController {
    private UserService userService;

    @PreAuthorize("hasAuthority('INTERNAL')")
    //create a user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto user=userService.createUser(userDto);
        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasAuthority('INTERNAL')")
    //get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
       List<UserDto> userDtoList = userService.getAllUsers();
       return ResponseEntity.ok(userDtoList);
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<?> updatePass(
            @PathVariable Long id,
            @RequestBody PasswordChangeRequest request
    ) {
        userService.updateUserPass(id, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAuthority('INTERNAL')")
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
    //update just image
    @PostMapping("/update-image/{id}")
    public ResponseEntity<String> updateImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = userService.updateUserImage(id, file);
        return ResponseEntity.ok(imageUrl);
    }

    //delete some one
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('INTERNAL')")
    @GetMapping("/{id}/exists")
    public Boolean checkUserExists(@PathVariable Long id) {
        System.out.println("==== checkUserExists ====");
        return userService.existsById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER ADMIN')")
    @GetMapping("/paginated")
    public ResponseEntity<Page<UserDto>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search
    ) {
        Page<UserDto> users = userService.getAllUsersPaginated(page, size,search);
        return ResponseEntity.ok(users);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER ADMIN')")
    @GetMapping("/filtered/paginated")
    public ResponseEntity<Page<UserDto>> getFilteredUsersPaginated(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role) {

        Page<UserDto> result;

        if (role == null || role.isEmpty()) {
            result = userService.getAllUsersPaginated(page, size,search);
        } else {
            result = userService.getUsersByRolePaginated(role, page, size);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = userService.uploadImage(id, file);
        return ResponseEntity.ok(imageUrl);
    }

    @PreAuthorize("hasAuthority('INTERNAL')")
    @GetMapping("/by-username/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return userDto;
    }
    @GetMapping("/mySession")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

}

















