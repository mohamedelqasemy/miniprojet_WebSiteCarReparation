package com.ensas.userservice.services;

import com.ensas.userservice.dtos.UserDto;
import com.ensas.userservice.entities.User;
import com.ensas.userservice.mappers.UserMapper;
import com.ensas.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto) {
        User newUser = UserMapper.toEntity(userDto);
        newUser.setEnabled(true);
        newUser.setRole("USER");
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
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Transactional
    public UserDto updateUser(Long id,UserDto userDto) {
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
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }

        return UserMapper.toDTO(existingUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
    }



}
