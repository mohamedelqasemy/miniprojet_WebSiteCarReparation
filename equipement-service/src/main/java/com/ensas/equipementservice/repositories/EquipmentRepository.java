package com.ensas.equipementservice.repositories;

import com.ensas.equipementservice.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

}
