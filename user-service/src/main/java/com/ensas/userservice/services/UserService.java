package com.ensas.userservice.services;

import com.ensas.userservice.dtos.CloudinaryResponse;
import com.ensas.userservice.dtos.UserDto;
import com.ensas.userservice.entities.User;
import com.ensas.userservice.mappers.UserMapper;
import com.ensas.userservice.repositories.UserRepository;
import com.ensas.userservice.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final KeycloakAdminClient keycloakAdminClient;

    public UserDto createUser(UserDto userDto) {
        User newUser = UserMapper.toEntity(userDto);
//        newUser.setEnabled(true);
//        newUser.setRole("USER");
        newUser.setCreated(new Date());

        newUser = userRepository.save(newUser);

        return UserMapper.toDTO(newUser);
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return UserMapper.toDTOList(userList);
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        if (userDto == null || id == null) {
            throw new IllegalArgumentException("Les données de l'utilisateur ne peuvent pas être nulles");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Mise à jour uniquement des champs fournis
        if (userDto.getFirstname() != null) {
            existingUser.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null) {
            existingUser.setLastname(userDto.getLastname());
        }
        if (userDto.getPassword() != null) {
            existingUser.setPassword(userDto.getPassword());
        }
        if (userDto.getRole() != null) {
            existingUser.setRole(userDto.getRole());
        }
        if (userDto.getAddress() != null){
            existingUser.setAddress(userDto.getAddress());
        }
        if(userDto.getEmail() != null){
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getNum() != null){
            existingUser.setNum(userDto.getNum());
        }
        existingUser.setEnabled(userDto.isEnabled());

        //modify information in keycloack
        keycloakAdminClient.updateUser(userDto);


        return UserMapper.toDTO(existingUser);
    }

    @Transactional
    public String updateUserImage(Long id, final MultipartFile file) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        String url = uploadImage(id,file);
        existingUser.setImage(url);

        return url;
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (user.getImage() != null) {
            cloudinaryService.deleteFileByUrl(user.getImage());
        }
        userRepository.delete(user);
    }


    public boolean existsById(Long id) {
        System.out.println("Checking if user exists with ID: " + id);
        return userRepository.existsById(id);
    }

    public Page<UserDto> getAllUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserMapper::toDTO);
    }

    public Page<UserDto> getUsersByRolePaginated(String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> userPage = userRepository.findByRole(role, pageable);
        return userPage.map(UserMapper::toDTO);
    }


    @Transactional
    public String uploadImage(final Long id, final MultipartFile file) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User non trouvée"));

        // Supprimer l'ancienne image si elle existe
        if (user.getImage() != null && !user.getImage().isEmpty()) {
            cloudinaryService.deleteFileByUrl(user.getImage());
        }

        // Upload de la nouvelle image
        CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(file, "user_" + id);
        user.setImage(cloudinaryResponse.getUrl());

        userRepository.save(user);
        return cloudinaryResponse.getUrl();
    }

    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec l'email : " + email));
    }
}
