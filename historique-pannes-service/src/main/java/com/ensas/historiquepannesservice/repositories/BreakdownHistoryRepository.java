package com.ensas.historiquepannesservice.repositories;

import com.ensas.historiquepannesservice.entities.BreakdownHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreakdownHistoryRepository extends JpaRepository<BreakdownHistory, Long> {
}
