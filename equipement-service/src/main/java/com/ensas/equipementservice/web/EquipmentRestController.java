package com.ensas.equipementservice.web;

import com.ensas.equipementservice.dtos.EquipmentDto;
import com.ensas.equipementservice.services.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipments")
@AllArgsConstructor()
public class EquipmentRestController {
    private EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<EquipmentDto>> getAllEquipments() {
        List<EquipmentDto> equipmentsDto = equipmentService.getAllEquipment();
        return ResponseEntity.ok(equipmentsDto);
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
}
