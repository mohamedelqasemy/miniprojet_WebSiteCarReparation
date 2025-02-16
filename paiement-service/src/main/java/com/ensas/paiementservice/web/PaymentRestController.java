package com.ensas.paiementservice.web;

import com.ensas.paiementservice.dtos.PaymentDto;
import com.ensas.paiementservice.entities.Payment;
import com.ensas.paiementservice.feign.UserRestClient;
import com.ensas.paiementservice.mappers.UserMapper;
import com.ensas.paiementservice.models.User;
import com.ensas.paiementservice.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentRestController {
    private PaymentService paymentService;
    private UserRestClient userRestClient;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getPayments() {
        List<PaymentDto> payments = paymentService.getAllPayment();
        payments.forEach(payment -> {
            User user = UserMapper.toEntity(userRestClient.getUserById(payment.getUserId()));
            payment.setUser(user);

                });
        return ResponseEntity.ok(payments);

    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long id) {
        PaymentDto payment = paymentService.getPaymentById(id);
        User user = UserMapper.toEntity(userRestClient.getUserById(payment.getUserId()));
        payment.setUser(user);
        System.out.println(userRestClient.getUserById(payment.getUserId()).toString());
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto payment) {
        PaymentDto createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(createdPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable("id") Long id, @RequestBody PaymentDto payment) {
        PaymentDto updatedPayment = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable("id") Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}
