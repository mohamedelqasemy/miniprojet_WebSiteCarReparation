package com.ensas.domicileservice.mappers;


import com.ensas.domicileservice.models.UserDto;
import com.ensas.domicileservice.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDTO(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .address(user.getAddress())
                .build();
    }

    public static User toEntity(UserDto userDTO) {
        if (userDTO == null) return null;

        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .role(userDTO.getRole())
                .address(userDTO.getAddress())
                .build();
    }

    public static List<UserDto> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<User> toEntityList(List<UserDto> userDTOs) {
        return userDTOs.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}
