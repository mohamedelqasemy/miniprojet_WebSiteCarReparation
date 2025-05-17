package com.ensas.reservationservice.web;

import com.ensas.reservationservice.dtos.ReservationDto;
import com.ensas.reservationservice.dtos.ReservationRequest;
import com.ensas.reservationservice.dtos.ReservationResponseDto;
import com.ensas.reservationservice.entities.Reservation;
import com.ensas.reservationservice.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    //new reservation
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.createReservation(reservationRequest);
        return ResponseEntity.ok(reservation);
    }

    //get all reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/paginated")
    public ResponseEntity<Page<ReservationResponseDto>> getAllReservationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search
    ) {
        Page<ReservationResponseDto> reservations = reservationService.getAllReservationsPaginated(page, size,search);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{userId}/paginated")
    public Page<ReservationResponseDto> getClientReservations(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return reservationService.getReservationsByClient(userId, pageable);
    }

    //gat a specific reservation
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //update a reservation
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
                                                         @RequestBody ReservationDto reservationDto) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDto);
        return ResponseEntity.ok(updatedReservation);
    }

    //delete a reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    //blocked days
    @GetMapping("/blocked-dates")
    public List<String> getBlockedDatesFromTomorrow(
            @RequestParam(defaultValue = "8") int maxPerDay
    ) {
        return reservationService.getBlockedDatesFromTomorrow(maxPerDay);
    }

}
