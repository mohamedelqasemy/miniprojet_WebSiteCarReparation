package com.ensas.reservationservice.services;

import com.ensas.reservationservice.Repositories.ReservationRepository;
import com.ensas.reservationservice.dtos.ReservationDto;
import com.ensas.reservationservice.dtos.ReservationRequest;
import com.ensas.reservationservice.dtos.ReservationResponseDto;
import com.ensas.reservationservice.entities.Reservation;
import com.ensas.reservationservice.enums.EnumStatus;
import com.ensas.reservationservice.feign.*;
import com.ensas.reservationservice.mappers.ReservationMapper;
import com.ensas.reservationservice.model.*;
import com.ensas.reservationservice.util.ReservationSpecification;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRestClient userRestClient;
    private final ServiceRestClient serviceRestClient;
    private final CarRestClient carRestClient;
    private final GarageRestClient garageRestClient;
    private final HistoryRestClient historyRestClient;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }


    public Page<ReservationResponseDto> getReservationsByClient(Long userId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByUserId(userId, pageable);

        return reservations.map(reservation -> {
            User user = userRestClient.getUserById(userId);
            List<Reparation> reparations = reservation.getServiceId().stream()
                    .map(serviceRestClient::getServiceById)
                    .collect(Collectors.toList());
            GarageDto garageDto = garageRestClient.getGarageById(reservation.getGarageId());

            return ReservationMapper.mapToDtoN(reservation, user, reparations, garageDto);
        });
    }

    public Reservation createReservation(ReservationRequest reservation) {
        // Récupération sécurisée des entités liées
        User user = getUserSafe(reservation.getUserId());
        List<Reparation> services = new ArrayList<>();
        for (Long id : reservation.getServiceId()) {
            services.add(getServiceSafe(id));
        }


        Reservation newReservation = new Reservation();
        newReservation.setDate(reservation.getDate());
        newReservation.setStatus(EnumStatus.Pending);
        newReservation.setGarageId(reservation.getGarageId());
        newReservation.setUserId(reservation.getUserId());
        newReservation.setServiceId(reservation.getServiceId());
        newReservation.setServiceNames(services.stream().map(Reparation::getName).collect(Collectors.toList()));

        CarDto carDto = CarDto.builder()
                .brand(reservation.getBrand())
                .model(reservation.getModel())
                .image("")
                .kilometers(reservation.getKilometers())
                .motorisation(reservation.getMotorisation())
                .licensePlate(reservation.getLicensePlate())
                .userId(user.getId())
                .build();

        Long myCarId = carRestClient.getCarIdByLicensePlate(reservation.getLicensePlate().trim());

        Long carId = myCarId;
        if (carId == 0) {
            CarDto newCar = carRestClient.createCar(carDto);
            carId = newCar.getId();
        }
        newReservation.setCarId(carId);

        BreakdownHistoryDto newHistory = BreakdownHistoryDto.builder()
                .carId(carId)
                .datePanne(reservation.getDate())
                .isRepaired(false)
                .nomPanne("fin njibo smiyat les pannes a drari")
                .description("o htta desc")
                .build();
        BreakdownHistoryDto createdHistory = historyRestClient.createBreakdownHistory(newHistory);


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

    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Long carId = reservation.getCarId();

        List<BreakdownHistoryDto> carHistories = historyRestClient.getHistoriesByCarId(carId);

        BreakdownHistoryDto targetHistory = carHistories.stream()
                .filter(h -> h.getDatePanne().equals(reservation.getDate()))
                .findFirst()
                .orElse(null);

        if (targetHistory != null) {
            historyRestClient.deleteHistory(targetHistory.getId());
            carHistories.remove(targetHistory);
        }

        if (carHistories.isEmpty()) {
            carRestClient.deleteCar(carId);
        }

        reservationRepository.deleteById(reservationId);
    }


    public Page<ReservationResponseDto> getAllReservationsPaginated(int page, int size,String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        Specification<Reservation> spec = Specification.where(null);
        spec = spec.and(ReservationSpecification.hasName(search));
        Page<Reservation> reservationPage = reservationRepository.findAll(spec,pageable);

        return reservationPage.map(reservation -> {
            GarageDto garage = garageRestClient.getGarageById(reservation.getGarageId());
            User user = userRestClient.getUserById(reservation.getUserId());
            List<Reparation> services = reservation.getServiceId().stream()
                    .map(serviceRestClient::getServiceById)
                    .collect(Collectors.toList());

            return ReservationMapper.mapToDtoN(reservation, user, services, garage);
        });
    }

    //for giving blocked days
    public List<String> getBlockedDatesFromTomorrow(int maxPerDay) {
        // Start from tomorrow
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = truncateTime(cal.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return reservationRepository.findAll().stream()
                .filter(res -> res.getStatus() != EnumStatus.Rejected)
                .map(res -> truncateTime(res.getDate()))
                .filter(date -> !date.before(tomorrow)) // Only from tomorrow onward
                .collect(Collectors.groupingBy(date -> date, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= maxPerDay)
                .map(entry -> sdf.format(entry.getKey())) // Format as string: yyyy-MM-dd
                .toList();
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

    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
