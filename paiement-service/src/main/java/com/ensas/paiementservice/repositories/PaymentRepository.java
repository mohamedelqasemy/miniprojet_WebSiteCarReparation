package com.ensas.paiementservice.repositories;

import com.ensas.paiementservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
