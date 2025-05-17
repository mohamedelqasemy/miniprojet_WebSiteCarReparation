package com.ensas.commandservice.repositories;

import com.ensas.commandservice.entities.Command;
import com.ensas.commandservice.enums.EnumStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Long> {
    List<Command> findByUserId(Long userId);
    int countByStatus(EnumStatus status);
    List<Command> findByStatus(EnumStatus status);
    Page<Command> findByUserId(Long userId, Pageable pageable);

}
