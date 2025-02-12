package com.ensas.userservice.mappers;

import com.ensas.userservice.dtos.UserDto;
import com.ensas.userservice.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // Convertir une Entité en DTO
    public static UserDto toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getRole());
    }

    // Convertir un DTO en Entité
    public static User toEntity(UserDto userDTO) {
        if (userDTO == null) {
            return null;
        }
        return new User(userDTO.getId(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstname(), userDTO.getLastname(), userDTO.getRole(), false,null);
    }

    // Convertir une liste d'Entités en liste de DTOs
    public static List<UserDto> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Convertir une liste de DTOs en liste d'Entités
    public static List<User> toEntityList(List<UserDto> userDTOs) {
        return userDTOs.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}

