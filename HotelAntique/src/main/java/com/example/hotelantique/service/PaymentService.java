package com.example.hotelantique.service;

import com.example.hotelantique.model.entity.Payment;
import com.example.hotelantique.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public void savePayment(Payment payment) {
        this.paymentRepository.save(payment);
    }
}
