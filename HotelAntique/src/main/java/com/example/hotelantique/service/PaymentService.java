package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.reservationDTO.PaymentReservationDTO;
import com.example.hotelantique.model.entity.Payment;
import com.example.hotelantique.model.entity.Reservation;
import com.example.hotelantique.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private final ReservationService reservationService;

    private final PaymentRepository paymentRepository;

    public PaymentService(PasswordEncoder passwordEncoder,
                          ModelMapper modelMapper, ReservationService reservationService,
                          PaymentRepository paymentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.reservationService = reservationService;
        this.paymentRepository = paymentRepository;
    }


    public void savePayment(Payment payment) {
        this.paymentRepository.save(payment);
    }

    public void addPayment(PaymentReservationDTO paymentReservationDTO, long reservationId) {

        Payment payment = this.modelMapper.map(paymentReservationDTO, Payment.class);
        payment.setCardNumber(passwordEncoder.encode(paymentReservationDTO.getCardNumber()));
        payment.setCvv(passwordEncoder.encode(paymentReservationDTO.getCvv()));
        Reservation reservation = this.reservationService.getReservation(reservationId);
        payment.setReservation(reservation);
        this.paymentRepository.save(payment);



    }

    public Payment createPayment(long reservationId) {
        Payment payment = new Payment();
        Reservation reservation = this.reservationService.getReservation(reservationId);
        payment.setReservation(reservation);
        return payment;
    }
}
