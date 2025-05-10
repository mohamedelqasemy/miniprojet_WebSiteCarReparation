package com.ensas.reservationservice.mappers;

import com.ensas.reservationservice.dtos.ReservationResponseDto;
import com.ensas.reservationservice.entities.Reservation;
import com.ensas.reservationservice.model.Reparation;
import com.ensas.reservationservice.model.ServiceDto;
import com.ensas.reservationservice.model.User;

import java.util.List;

public class ReservationMapper {
    public static ReservationResponseDto mapToDtoN(Reservation reservation, User user, List<Reparation> reparations) {
        ReservationResponseDto dto = new ReservationResponseDto();

        dto.setId(reservation.getId());
        dto.setDate(reservation.getDate().toString());
        dto.setStatus(reservation.getStatus());
        dto.setClient(user.getLastname() + " " + user.getFirstname());
        dto.setCarId(reservation.getCarId());
        dto.setGarageId(reservation.getGarageId());

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


//    public static ReservationResponseDto mapToDto(Reservation reservation, User user, Reparation reparation) {
//        ReservationResponseDto dto = new ReservationResponseDto();
//
//        dto.setId(reservation.getId());
//        dto.setDate(reservation.getDate().toString());
//        dto.setStatus(reservation.getStatus());
//
//        dto.setClient(user.getLastname()+" "+user.getFirstname());
//        dto.setAdress(user.getEmail());
//        dto.setName(reparation.getName());
//        dto.setPrix(reparation.getServicePrice());
//        dto.setTypeService(reparation.getName());
//        dto.setImage(reparation.getImage());
//        return dto;
//    }
}
