package com.ensas.equipementservice.services;

import com.ensas.equipementservice.dtos.EquipmentDto;
import com.ensas.equipementservice.entities.Equipment;
import com.ensas.equipementservice.mappers.EquipmentMapper;
import com.ensas.equipementservice.repositories.EquipmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentDto createEquipment(EquipmentDto equipmentDto) {
        Equipment equipment = EquipmentMapper.toEntity(equipmentDto);
        equipment = equipmentRepository.save(equipment);
        return EquipmentMapper.toDTO(equipment);
    }

    public List<EquipmentDto> getAllEquipment() {
        List<Equipment> equipments = equipmentRepository.findAll();
        return EquipmentMapper.toDTOList(equipments);
    }

    public EquipmentDto getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .map(EquipmentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));
    }
    public EquipmentDto updateEquipment(Long id, EquipmentDto equipmentDto) {
        if(id == null || equipmentDto == null) {
            throw new IllegalArgumentException("Les données de l'équipement ne peuvent pas être nulles");
        }
        Equipment existingEquipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));

        if(equipmentDto.getDescription() != null) {
            existingEquipment.setDescription(equipmentDto.getDescription());
        }
        if(equipmentDto.getName() != null) {
            existingEquipment.setName(equipmentDto.getName());
        }
        if(equipmentDto.getPrice() != null) {
            existingEquipment.setPrice(equipmentDto.getPrice());
        }
        if(equipmentDto.getQuantity() != null) {
            existingEquipment.setQuantity(equipmentDto.getQuantity());
        }
        return EquipmentMapper.toDTO(existingEquipment);
    }

    public void deleteEquipment(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new RuntimeException("Equipement non trouvé");
        }
        equipmentRepository.deleteById(id);
    }


}
