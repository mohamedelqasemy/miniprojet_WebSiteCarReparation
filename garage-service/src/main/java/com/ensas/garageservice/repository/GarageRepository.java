package com.ensas.garageservice.repository;

import com.ensas.garageservice.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}
