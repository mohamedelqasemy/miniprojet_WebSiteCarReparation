package com.ensas.domicileservice.mappers;

import com.ensas.domicileservice.dtos.RequestHomeDto;
import com.ensas.domicileservice.entities.RequestHome;

import java.util.List;
import java.util.stream.Collectors;

public class RequestHomeMapper {
    public static RequestHomeDto toRequestHomeDto(RequestHome requestHome){
        if (requestHome == null)
            return null;
        return RequestHomeDto.builder()
                .id(requestHome.getId())
                .userId(requestHome.getUserId())
                .user(requestHome.getUser())
                .address(requestHome.getAddress())
                .serviceType(requestHome.getServiceType())
                .dateDemand(requestHome.getDateDemand())
                .status(requestHome.getStatus())
                .build();
    }

    public static RequestHome toRequestHome(RequestHomeDto requestHomeDto){
        if (requestHomeDto == null)
            return null;
        return RequestHome.builder()
                .id(requestHomeDto.getId())
                .userId(requestHomeDto.getUserId())
                .user(requestHomeDto.getUser())
                .address(requestHomeDto.getAddress())
                .serviceType(requestHomeDto.getServiceType())
                .dateDemand(requestHomeDto.getDateDemand())
                .status(requestHomeDto.getStatus())
                .build();
    }

    public static List<RequestHomeDto> toRequestHomeDtoList(List<RequestHome> requestHomeList){
        if (requestHomeList == null)
            return null;
        return requestHomeList.stream().map(RequestHomeMapper::toRequestHomeDto).collect(Collectors.toList());
    }

    public static List<RequestHome> toRequestHomeList(List<RequestHomeDto> requestHomeDtoList){
        if (requestHomeDtoList == null)
            return null;
        return requestHomeDtoList.stream().map(RequestHomeMapper::toRequestHome).collect(Collectors.toList());
    }
}
