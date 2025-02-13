package com.ensas.commandservice.repositories;

import com.ensas.commandservice.entities.CommandDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandDetailsRepository extends JpaRepository<CommandDetails, Integer> {
}
