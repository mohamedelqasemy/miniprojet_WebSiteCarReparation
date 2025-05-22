package com.ensas.carservice.repositories;

import com.ensas.carservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long>, JpaSpecificationExecutor<Car> {
    Optional<Car> findByLicensePlate(String licensePlate);
}
