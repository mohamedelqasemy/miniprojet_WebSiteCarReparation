package com.ensas.reservationservice.services;

import com.ensas.reservationservice.Repositories.ReservationRepository;
import com.ensas.reservationservice.dtos.ReservationDto;
import com.ensas.reservationservice.entities.Reservation;
import com.ensas.reservationservice.enums.EnumStatus;
import com.ensas.reservationservice.feign.ServiceRestClient;
import com.ensas.reservationservice.feign.UserRestClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRestClient userRestClient;
    private final ServiceRestClient serviceRestClient;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(ReservationDto reservation) {
        Reservation newReservation = new Reservation();
        newReservation.setDate(reservation.getDate());
        newReservation.setStatus(EnumStatus.Pending);

        //verify if user exist
        try {
            if (userRestClient.getUserById(reservation.getUserId()) == null) {
                throw new RuntimeException("Utilisateur non trouvé");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'utilisateur : " + e.getMessage());
        }

        //verify if service is exist
        try {
            if (serviceRestClient.getServiceById(reservation.getServiceId()) == null) {
                throw new RuntimeException("Service non trouvé");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification du service : " + e.getMessage());
        }

        //verify if car is exist
        // if (carRestClient.getCarById(reservation.getCarId()) == null) {
        //     throw new RuntimeException("Voiture non trouvée");
        // }

        newReservation.setUserId(reservation.getUserId());
        newReservation.setServiceId(reservation.getServiceId());
        newReservation.setCarId(reservation.getCarId());

        reservationRepository.save(newReservation);
        return newReservation;
    }

    public Reservation updateReservation(Long id, ReservationDto reservationDto) {
        Reservation resUpdated = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        //mis a jour les chose envoyer par client side
        if (reservationDto.getDate() != null) {
            resUpdated.setDate(reservationDto.getDate());
        }
        if (reservationDto.getStatus() != null) {
            resUpdated.setStatus(reservationDto.getStatus());
        }

        return reservationRepository.save(resUpdated);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
