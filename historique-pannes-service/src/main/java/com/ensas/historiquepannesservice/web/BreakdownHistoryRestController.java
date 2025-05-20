package com.ensas.historiquepannesservice.web;

import com.ensas.historiquepannesservice.dtos.BreakdownHistoryDto;
import com.ensas.historiquepannesservice.feign.CarRestClient;
import com.ensas.historiquepannesservice.mappers.CarMapper;
import com.ensas.historiquepannesservice.models.Car;
import com.ensas.historiquepannesservice.services.BreakdownHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/histories")
@AllArgsConstructor
@EnableFeignClients
public class BreakdownHistoryRestController {
    private BreakdownHistoryService breakdownHistoryService;
    private CarRestClient carRestClient;

    @GetMapping
    public ResponseEntity<List<BreakdownHistoryDto>> getBreakdownHistory() {
        List<BreakdownHistoryDto> histories = breakdownHistoryService.getAllBreakdownHistory();
        histories.forEach(history -> {
            Car car = CarMapper.toCar(carRestClient.getCarById(history.getCarId()));
            history.setCar(CarMapper.toCarDto(car));

                });
        return ResponseEntity.ok(histories);

    }
    @GetMapping("/{id}")
    public ResponseEntity<BreakdownHistoryDto> getBreakdownHistoryById(@PathVariable("id") Long id) {
        BreakdownHistoryDto history = breakdownHistoryService.getBreakdownHistoryById(id);
        Car car = CarMapper.toCar(carRestClient.getCarById(history.getCarId()));
        history.setCar(CarMapper.toCarDto(car));
        System.out.println(carRestClient.getCarById(history.getCarId()).toString());
        return ResponseEntity.ok(history);
    }

    @PostMapping
    public ResponseEntity<BreakdownHistoryDto> createBreakdownHistory(@RequestBody BreakdownHistoryDto historyDto) {
        BreakdownHistoryDto createdHistory = breakdownHistoryService.createBreakdownHistory(historyDto);
        return ResponseEntity.ok(createdHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreakdownHistoryDto> updateHistory(@PathVariable("id") Long id, @RequestBody BreakdownHistoryDto historyDto) {
        BreakdownHistoryDto updatedHistory = breakdownHistoryService.updateBreakdownHistory(id, historyDto);
        return ResponseEntity.ok(updatedHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistory(@PathVariable("id") Long id) {
        breakdownHistoryService.deleteBreakdownHistory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<BreakdownHistoryDto>> getAllBreaksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BreakdownHistoryDto> breaks = breakdownHistoryService.getAllBreaksPaginated(page, size);
        return ResponseEntity.ok(breaks);
    }

    @GetMapping("/paginated/{carId}")
    public ResponseEntity<Page<BreakdownHistoryDto>> getBreaksPaginatedByCarId(
            @PathVariable("carId") Long carId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<BreakdownHistoryDto> breaks = breakdownHistoryService.getBreaksPaginatedByCarId(carId,page, size);
        return ResponseEntity.ok(breaks);
    }

    @GetMapping("/by-car/{carId}")
    public ResponseEntity<List<BreakdownHistoryDto>> getHistoriesByCarId(@PathVariable Long carId) {
        List<BreakdownHistoryDto> histories = breakdownHistoryService.getBreakdownHistoriesByCarId(carId);
        return ResponseEntity.ok(histories);
    }

}
