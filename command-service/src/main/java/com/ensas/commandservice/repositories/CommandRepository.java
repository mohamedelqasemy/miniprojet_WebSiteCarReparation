package com.ensas.commandservice.repositories;

import com.ensas.commandservice.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Long> {
    List<Command> findByUserId(Long userId);
}
