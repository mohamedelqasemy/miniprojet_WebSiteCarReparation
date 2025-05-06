package com.ensas.reservationservice.mappers;

import com.ensas.reservationservice.dtos.ReservationResponseDto;
import com.ensas.reservationservice.entities.Reservation;
import com.ensas.reservationservice.model.Reparation;
import com.ensas.reservationservice.model.User;

public class ReservationMapper {
    public static ReservationResponseDto mapToDto(Reservation reservation, User user, Reparation reparation) {
        ReservationResponseDto dto = new ReservationResponseDto();

        dto.setId(reservation.getId());
        dto.setDate(reservation.getDate().toString());
        dto.setStatus(reservation.getStatus());

        dto.setClient(user.getLastname()+" "+user.getFirstname());
        dto.setAdresse(user.getEmail());
        dto.setName(reparation.getName());
        dto.setPrix(reparation.getServicePrice());
        dto.setTypeService(reparation.getName());
        dto.setImage(reparation.getImage());
        return dto;
    }
}
