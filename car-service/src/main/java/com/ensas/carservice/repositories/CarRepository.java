package com.ensas.carservice.repositories;

import com.ensas.carservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {
    Optional<Car> findByLicensePlate(String licensePlate);
}
