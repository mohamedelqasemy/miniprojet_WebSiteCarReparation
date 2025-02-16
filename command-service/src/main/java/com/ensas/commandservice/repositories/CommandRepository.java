package com.ensas.commandservice.repositories;

import com.ensas.commandservice.entities.Command;
import org.springframework.data.repository.CrudRepository;

public interface CommandRepository extends CrudRepository<Command, Long> {
}
