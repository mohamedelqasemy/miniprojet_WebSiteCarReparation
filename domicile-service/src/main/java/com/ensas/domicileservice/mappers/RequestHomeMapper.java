package com.ensas.domicileservice.mappers;

import com.ensas.domicileservice.dtos.RequestHomeDto;
import com.ensas.domicileservice.dtos.RequestHomeResponse;
import com.ensas.domicileservice.entities.RequestHome;
import com.ensas.domicileservice.models.Reparation;
import com.ensas.domicileservice.models.ServiceDto;
import com.ensas.domicileservice.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class RequestHomeMapper {

    public static RequestHomeResponse mapToDtoN(RequestHome reservation, User user, List<Reparation> reparations) {
        RequestHomeResponse dto = new RequestHomeResponse();

        dto.setId(reservation.getId());
        dto.setDate(reservation.getDateDemand().toString());
        dto.setStatus(reservation.getStatus());
        dto.setClient(user.getLastname() + " " + user.getFirstname());
        dto.setAddress(reservation.getAddress());

        List<ServiceDto> serviceDtos = reparations.stream().map(rep -> {
            ServiceDto s = new ServiceDto();
            s.setName(rep.getName());
            s.setPrice(rep.getServicePrice());
            s.setImage(rep.getImage());
            s.setTypeService(rep.getName());
            return s;
        }).toList();

        dto.setServices(serviceDtos);

        return dto;
    }
    public static RequestHomeDto toRequestHomeDto(RequestHome requestHome){
        if (requestHome == null)
            return null;
        return RequestHomeDto.builder()
                .id(requestHome.getId())
                .userId(requestHome.getUserId())
                .address(requestHome.getAddress())
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
                .address(requestHomeDto.getAddress())
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
