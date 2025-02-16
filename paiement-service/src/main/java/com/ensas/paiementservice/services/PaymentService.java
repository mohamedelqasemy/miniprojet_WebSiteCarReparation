package com.ensas.paiementservice.services;

import com.ensas.paiementservice.dtos.PaymentDto;
import com.ensas.paiementservice.entities.Payment;
import com.ensas.paiementservice.mappers.PaymentMapper;
import com.ensas.paiementservice.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = PaymentMapper.toEntity(paymentDto);
        payment = paymentRepository.save(payment);
        return PaymentMapper.toDTO(payment);
    }

    public List<PaymentDto> getAllPayment() {
        List<Payment> payments = paymentRepository.findAll();
        return PaymentMapper.toDTO(payments);
    }

    public PaymentDto getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(PaymentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        if(id == null || paymentDto == null) {
            throw new IllegalArgumentException("Les données de paiement ne peuvent pas être nulles");
        }
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));

        if(paymentDto.getAmount() != null) {
            existingPayment.setAmount(paymentDto.getAmount());
        }
        if(paymentDto.getType() != null) {
            existingPayment.setType(paymentDto.getType());
        }
        if(paymentDto.getDate() != null) {
            existingPayment.setDate(paymentDto.getDate());
        }
        return PaymentMapper.toDTO(existingPayment);
    }

    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Paiement non trouvé");
        }
        paymentRepository.deleteById(id);
    }

}
