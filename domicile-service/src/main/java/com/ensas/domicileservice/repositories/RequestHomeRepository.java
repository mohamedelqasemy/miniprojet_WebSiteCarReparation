package com.ensas.domicileservice.repositories;

import com.ensas.domicileservice.entities.RequestHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestHomeRepository extends JpaRepository<RequestHome,Long>, JpaSpecificationExecutor<RequestHome> {
}
