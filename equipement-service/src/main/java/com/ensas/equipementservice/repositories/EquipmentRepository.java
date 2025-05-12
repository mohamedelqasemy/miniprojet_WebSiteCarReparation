package com.ensas.equipementservice.repositories;

import com.ensas.equipementservice.entities.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {
    @Query("SELECT e FROM Equipment e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Equipment> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
