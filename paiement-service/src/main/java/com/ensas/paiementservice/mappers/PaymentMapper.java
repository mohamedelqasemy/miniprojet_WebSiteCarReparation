package com.ensas.paiementservice.mappers;

import com.ensas.paiementservice.dtos.PaymentDto;
import com.ensas.paiementservice.entities.Payment;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {

    public static PaymentDto toDTO(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        BeanUtils.copyProperties(payment, paymentDto);
        return paymentDto;
    }

    public static Payment toEntity(PaymentDto paymentDto) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentDto, payment);
        return payment;
    }

    public static List<PaymentDto> toDTO(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Payment> toEntity(List<PaymentDto> paymentsDto) {
        return paymentsDto.stream()
                .map(PaymentMapper::toEntity)
                .collect(Collectors.toList());
    }

}
