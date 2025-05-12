package com.ensas.equipementservice.web;

import com.ensas.equipementservice.dtos.EquipmentDto;
import com.ensas.equipementservice.services.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/equipments")
@AllArgsConstructor()
public class EquipmentRestController {
    private final EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<EquipmentDto>> getAllEquipments() {
        List<EquipmentDto> equipmentsDto = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipmentsDto);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<EquipmentDto>> getAllEquipments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<EquipmentDto> equipmentsDto = equipmentService.getAllEquipmentPaginated(page, size);
        return ResponseEntity.ok(equipmentsDto);
    }

    @GetMapping("/filtered/paginated")
    public ResponseEntity<Page<EquipmentDto>> getEquipmentsFiltered(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String keyword) {

        Page<EquipmentDto> result;
        if (keyword == null || keyword.isEmpty()) {
            result = equipmentService.getAllEquipmentPaginated(page, size);
        } else {
            result = equipmentService.searchEquipmentsPaginated(keyword, page, size);
        }

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDto> getEquipmentById(@PathVariable("id") Long id) {
        EquipmentDto equipment = equipmentService.getEquipmentById(id);
        return ResponseEntity.ok(equipment);
    }

    @PostMapping
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody EquipmentDto equipmentDto) {
        return ResponseEntity.ok(equipmentService.createEquipment(equipmentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable("id") Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDto> updateEquipment(@PathVariable("id") Long id, @RequestBody EquipmentDto equipmentDto) {
        EquipmentDto equipment= equipmentService.updateEquipment(id, equipmentDto);
        return ResponseEntity.ok(equipment);

    }
    @GetMapping("/filtered")
    public ResponseEntity<Page<EquipmentDto>> getAllEquipmentPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) List<String> car,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        Page<EquipmentDto> equipments = equipmentService.getEquipmentPaginated(car, type, name, minPrice, maxPrice, page, size);
        return ResponseEntity.ok(equipments);
    }


    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = equipmentService.uploadImage(id, file);
        return ResponseEntity.ok(imageUrl);
    }

}
