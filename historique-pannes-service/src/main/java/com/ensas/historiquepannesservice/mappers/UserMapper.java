package com.ensas.historiquepannesservice.mappers;



import com.ensas.historiquepannesservice.models.User;
import com.ensas.historiquepannesservice.models.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDTO(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .num(user.getNum())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .created(user.getCreated())
                .image(user.getImage())
                .publicId(user.getPublicId())
                .address(user.getAddress())
                .build();
    }

    public static User toEntity(UserDto userDTO) {
        if (userDTO == null) return null;

        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .role(userDTO.getRole())
                .num(userDTO.getNum())
                .address(userDTO.getAddress())
                .created(userDTO.getCreated())
                .enabled(false)
                .image(userDTO.getImage())
                .publicId(userDTO.getPublicId())
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
