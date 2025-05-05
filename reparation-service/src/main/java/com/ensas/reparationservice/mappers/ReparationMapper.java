package com.ensas.reparationservice.mappers;

import com.ensas.reparationservice.dtos.ReparationDto;
import com.ensas.reparationservice.entities.Reparation;

import java.util.List;
import java.util.stream.Collectors;

public class ReparationMapper {

    public static ReparationDto toReparationDto(Reparation reparation){
        if(reparation == null)
            return null;
        return ReparationDto.builder()
                .id(reparation.getId())
                .name(reparation.getName())
                .description(reparation.getDescription())
                .type(reparation.getType())
                .dateOfCreation(reparation.getDateOfCreation())
                .servicePrice(reparation.getServicePrice())
                .image(reparation.getImage())
                .build();
    }

    public static Reparation toReparation(ReparationDto reparationDto){
        if (reparationDto == null)
            return null;
        return Reparation.builder()
                .id(reparationDto.getId())
                .name(reparationDto.getName())
                .description(reparationDto.getDescription())
                .type(reparationDto.getType())
                .dateOfCreation(reparationDto.getDateOfCreation())
                .servicePrice(reparationDto.getServicePrice())
                .image(reparationDto.getImage())
                .build();
    }

    public static List<ReparationDto> toReparationDtoList(List<Reparation> reparationList){
        if (reparationList == null)
            return null;
        return reparationList.stream().map(ReparationMapper::toReparationDto).collect(Collectors.toList());
    }

    public static List<Reparation> toReparationList(List<ReparationDto> reparationDtoList){
        if (reparationDtoList == null)
            return null;
        return reparationDtoList.stream().map(ReparationMapper::toReparation).collect(Collectors.toList());
    }
}
