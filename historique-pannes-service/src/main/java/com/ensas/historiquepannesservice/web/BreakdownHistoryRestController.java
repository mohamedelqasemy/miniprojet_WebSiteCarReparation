package com.ensas.historiquepannesservice.web;

import com.ensas.historiquepannesservice.dtos.BreakdownHistoryDto;
import com.ensas.historiquepannesservice.feign.CarRestClient;
import com.ensas.historiquepannesservice.feign.NotificationRestClient;
import com.ensas.historiquepannesservice.feign.UserRestClient;
import com.ensas.historiquepannesservice.mappers.CarMapper;
import com.ensas.historiquepannesservice.mappers.UserMapper;
import com.ensas.historiquepannesservice.models.*;
import com.ensas.historiquepannesservice.services.BreakdownHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/histories")
@AllArgsConstructor
@EnableFeignClients
public class BreakdownHistoryRestController {
    private BreakdownHistoryService breakdownHistoryService;
    private CarRestClient carRestClient;
    private UserRestClient userRestClient;
    private NotificationRestClient notificationRestClient;

    @GetMapping
    public ResponseEntity<List<BreakdownHistoryDto>> getBreakdownHistory() {
        List<BreakdownHistoryDto> histories = breakdownHistoryService.getAllBreakdownHistory();
        histories.forEach(history -> {
            Car car = CarMapper.toCar(carRestClient.getCarById(history.getCarId()));
            history.setCar(CarMapper.toCarDto(car));
            UserDto user = userRestClient.getUserById(history.getUserId());
            history.setUser(user);
        });
        return ResponseEntity.ok(histories);

    }
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BreakdownHistoryDto> getBreakdownHistoryById(@PathVariable("id") Long id) {
        BreakdownHistoryDto history = breakdownHistoryService.getBreakdownHistoryById(id);
        Car car = CarMapper.toCar(carRestClient.getCarById(history.getCarId()));
        history.setCar(CarMapper.toCarDto(car));
        UserDto user = userRestClient.getUserById(history.getUserId());
        history.setUser(user);
        System.out.println(carRestClient.getCarById(history.getCarId()).toString());
        return ResponseEntity.ok(history);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER ADMIN','INTERNAL')")
    @PostMapping
    public ResponseEntity<BreakdownHistoryDto> createBreakdownHistory(@RequestBody BreakdownHistoryDto historyDto) {
        Long userId = carRestClient.getUser(historyDto.getCarId());
        historyDto.setUserId(userId);
        BreakdownHistoryDto createdHistory = breakdownHistoryService.createBreakdownHistory(historyDto);
        return ResponseEntity.ok(createdHistory);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BreakdownHistoryDto> updateHistory(@PathVariable("id") Long id, @RequestBody BreakdownHistoryDto historyDto) {
        CarDto car = carRestClient.getCarById(historyDto.getCarId());
        UserDto user = userRestClient.getUserById(historyDto.getUserId());
        ReservationNotification notif = new ReservationNotification("etat de votre voiture",user.getLastname(),historyDto.getUserId(),new Date(),car.getLicensePlate(), user.getEmail());
        notificationRestClient.send(notif);

        BreakdownHistoryDto updatedHistory = breakdownHistoryService.updateBreakdownHistory(id, historyDto);
        return ResponseEntity.ok(updatedHistory);
    }

    @PreAuthorize("hasAuthority('INTERNAL')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistory(@PathVariable("id") Long id) {
        breakdownHistoryService.deleteBreakdownHistory(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER ADMIN')")
    @GetMapping("/paginated")
    public ResponseEntity<Page<BreakdownHistoryDto>> getAllBreaksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BreakdownHistoryDto> breaks = breakdownHistoryService.getAllBreaksPaginated(page, size);
        return ResponseEntity.ok(breaks);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER ADMIN')")
    @GetMapping("/paginated/{carId}")
    public ResponseEntity<Page<BreakdownHistoryDto>> getBreaksPaginatedByCarId(
            @PathVariable("carId") Long carId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BreakdownHistoryDto> breaks = breakdownHistoryService.getBreaksPaginatedByCarId(carId,page, size);
        return ResponseEntity.ok(breaks);
    }

    @PreAuthorize("hasAuthority('INTERNAL')")
    @GetMapping("/by-car/{carId}")
    public ResponseEntity<List<BreakdownHistoryDto>> getHistoriesByCarId(@PathVariable Long carId) {
        List<BreakdownHistoryDto> histories = breakdownHistoryService.getBreakdownHistoriesByCarId(carId);
        return ResponseEntity.ok(histories);
    }

}
