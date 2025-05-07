package com.ensas.garageservice.mapper;

import com.ensas.garageservice.dto.GarageDto;
import com.ensas.garageservice.entity.Garage;

import java.util.List;
import java.util.stream.Collectors;

public class GarageMapper {

    public static GarageDto toDto(Garage garage) {
        return GarageDto.builder()
                .id(garage.getId())
                .nom(garage.getNom())
                .adresse(garage.getAdresse())
                .dateCreation(garage.getDateCreation())
                .FermetureDate(garage.getFermetureDate())
                .image(garage.getImage())
                .note(garage.getNote())
                .OuvertureDate(garage.getOuvertureDate())
                .telephone(garage.getTelephone())
                .build();
    }

    public static Garage toEntity(GarageDto garageDto) {
        return Garage.builder()
                .nom(garageDto.getNom())
                .adresse(garageDto.getAdresse())
                .dateCreation(garageDto.getDateCreation())
                .FermetureDate(garageDto.getFermetureDate())
                .image(garageDto.getImage())
                .note(garageDto.getNote())
                .OuvertureDate(garageDto.getOuvertureDate())
                .telephone(garageDto.getTelephone())
                .build();
    }

    public static List<GarageDto> toDTOList(List<Garage> garages) {
        return garages.stream()
                .map(GarageMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Garage> toEntityList(List<GarageDto> garageDtos) {
        return garageDtos.stream()
                .map(GarageMapper::toEntity)
                .collect(Collectors.toList());
    }

}
