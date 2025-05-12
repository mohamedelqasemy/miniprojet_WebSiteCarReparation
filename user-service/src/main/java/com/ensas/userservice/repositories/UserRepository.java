package com.ensas.userservice.repositories;

import com.ensas.userservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByRole(String role, Pageable pageable);
}
