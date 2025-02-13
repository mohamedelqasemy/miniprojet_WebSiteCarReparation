package com.ensas.reparationservice.repositories;

import com.ensas.reparationservice.entities.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparationRepository extends JpaRepository<Reparation,Long> {
}
