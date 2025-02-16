package com.ensas.equipementservice.mappers;

import com.ensas.equipementservice.dtos.EquipmentDto;
import com.ensas.equipementservice.entities.Equipment;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class EquipmentMapper {

    public static EquipmentDto toDTO(Equipment equipment) {
        EquipmentDto equipmentDto = new EquipmentDto();
        BeanUtils.copyProperties(equipment, equipmentDto);
        return equipmentDto;
    }

    public static Equipment toEntity(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment();
        BeanUtils.copyProperties(equipmentDto, equipment);
        return equipment;
    }

    public static List<EquipmentDto> toDTOList(List<Equipment> equipments) {
        return equipments.stream()
                .map(EquipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Equipment> toEntityList(List<EquipmentDto> equipments) {
        return equipments.stream()
                .map(EquipmentMapper::toEntity)
                .collect(Collectors.toList());
    }
}
