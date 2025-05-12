package com.ensas.historiquepannesservice.repositories;

import com.ensas.historiquepannesservice.entities.BreakdownHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BreakdownHistoryRepository extends JpaRepository<BreakdownHistory, Long> {
    Page<BreakdownHistory> findByCarId(Long carId, Pageable pageable);
    List<BreakdownHistory> findByIsRepairedTrue();
}
