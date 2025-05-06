package com.ensas.reservationservice.services;

import com.ensas.reservationservice.Repositories.ReservationRepository;
import com.ensas.reservationservice.dtos.ReservationDto;
import com.ensas.reservationservice.dtos.ReservationResponseDto;
import com.ensas.reservationservice.entities.Reservation;
import com.ensas.reservationservice.enums.EnumStatus;
import com.ensas.reservationservice.feign.ServiceRestClient;
import com.ensas.reservationservice.feign.UserRestClient;
import com.ensas.reservationservice.mappers.ReservationMapper;
import com.ensas.reservationservice.model.Reparation;
import com.ensas.reservationservice.model.User;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
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

    public Reservation createReservation(ReservationDto reservationDto) {
        // Récupération sécurisée des entités liées
        User user = getUserSafe(reservationDto.getUserId());
        Reparation service = getServiceSafe(reservationDto.getServiceId());

        Reservation newReservation = new Reservation();
        newReservation.setDate(reservationDto.getDate());
        newReservation.setStatus(EnumStatus.Pending);
        newReservation.setUserId(reservationDto.getUserId());
        newReservation.setServiceId(reservationDto.getServiceId());
        newReservation.setCarId(reservationDto.getCarId());

        // Logs si des valeurs par défaut sont utilisées
        if ("Default".equals(user.getFirstname())) {
            log.warn("Création avec utilisateur par défaut (ID : {})", reservationDto.getUserId());
        }

        if ("Service inconnu".equals(service.getName())) {
            log.warn("Création avec service par défaut (ID : {})", reservationDto.getServiceId());
        }

        return reservationRepository.save(newReservation);
    }

    public Reservation updateReservation(Long id, ReservationDto reservationDto) {
        Reservation resUpdated = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        // Mise à jour des champs envoyés par le client
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

    public Page<ReservationResponseDto> getAllReservationsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        return reservationPage.map(reservation -> {
            User user = userRestClient.getUserById(reservation.getUserId());
            Reparation service = serviceRestClient.getServiceById(reservation.getServiceId());
            return ReservationMapper.mapToDto(reservation, user, service);
        });
    }

    // ====================
    // Méthodes privées de sécurisation
    // ====================

    private User getUserSafe(Long userId) {
        try {
            User user = userRestClient.getUserById(userId);
            if (user == null) throw new Exception("Utilisateur nul");
            return user;
        } catch (FeignException.NotFound e) {
            log.warn("Utilisateur non trouvé (ID : {}). Utilisation d’un utilisateur par défaut. Erreur : {}", userId, e.getMessage());
            User defaultUser = new User();
            defaultUser.setFirstname("Default");
            defaultUser.setLastname("User");
            defaultUser.setEmail("anonyme@example.com");
            return defaultUser;
        } catch (Exception e) {
            log.warn("Erreur lors de la récupération de l'utilisateur (ID : {}). Utilisation d’un utilisateur par défaut. Erreur : {}", userId, e.getMessage());
            User defaultUser = new User();
            defaultUser.setFirstname("Default");
            defaultUser.setLastname("User");
            defaultUser.setEmail("anonyme@example.com");
            return defaultUser;
        }
    }

    private Reparation getServiceSafe(Long serviceId) {
        try {
            Reparation service = serviceRestClient.getServiceById(serviceId);
            if (service == null) throw new Exception("Service nul");
            return service;
        } catch (Exception e) {
            log.warn("Service non trouvé (ID : {}). Utilisation d’un service par défaut. Erreur : {}", serviceId, e.getMessage());
            Reparation defaultService = new Reparation();
            defaultService.setName("Service inconnu");
            defaultService.setServicePrice(0.0);
            defaultService.setImage("default.jpg");
            return defaultService;
        }
    }
}
