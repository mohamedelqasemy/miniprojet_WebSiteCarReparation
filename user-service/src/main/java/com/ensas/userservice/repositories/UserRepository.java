package com.ensas.userservice.repositories;

import com.ensas.userservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Page<User> findByRole(String role, Pageable pageable);
    Optional<User> findByEmail(String email);
}
