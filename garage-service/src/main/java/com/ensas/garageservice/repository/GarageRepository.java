package com.ensas.garageservice.repository;

import com.ensas.garageservice.entity.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GarageRepository extends JpaRepository<Garage, Long> {
    @Query("SELECT g FROM Garage g WHERE LOWER(g.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Garage> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
