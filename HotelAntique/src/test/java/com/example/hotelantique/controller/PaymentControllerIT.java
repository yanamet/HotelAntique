package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.PaymentReservationDTO;
import com.example.hotelantique.model.entity.Payment;
import com.example.hotelantique.model.entity.Reservation;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.service.PaymentService;
import com.example.hotelantique.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ReservationService reservationService;

    Payment payment;
    PaymentReservationDTO paymentReservationDTO;
    Reservation reservation;
    UserEntity guest;

    @BeforeEach
    void setUp() {

        guest = new UserEntity();
        guest.setFullName("Guest Gestov");
        guest.setId(1L);
        guest.setUsername("Guest");
        guest.setPassword("topsecret");
        guest.setEmail("guest@gestov.bg");
        guest.setGender("MALE");
        guest.setPhoneNumber("00000000001");

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setActive(true);
        reservation.setGuest(guest);
        reservation.setTotalValue(BigDecimal.valueOf(90));
        reservation.setCheckOut(LocalDate.of(2023, 4, 20));
        reservation.setCheckIn(LocalDate.of(2023, 4, 15));

        payment = new Payment();
        payment.setReservation(reservation);
        payment.setCvv("123");
        payment.setCardNumber("1234567890");
        payment.setExpirationMonth("MARCH");
        payment.setExpirationYear(2024);
        payment.setId(1L);
        payment.setOwner("Guest");

        this.paymentService.savePayment(payment);

        paymentReservationDTO = new PaymentReservationDTO();
        paymentReservationDTO.setReservationId(1L);
        paymentReservationDTO.setCvv(payment.getCvv());
        paymentReservationDTO.setOwner(payment.getOwner());
        paymentReservationDTO.setExpirationYear(payment.getExpirationYear());
        paymentReservationDTO.setExpirationMonth(payment.getExpirationMonth());
        paymentReservationDTO.setCardNumber(payment.getCardNumber());

        when(this.reservationService.getReservation(1L))
                .thenReturn(reservation);

        when(this.paymentService.createPayment(1L))
                .thenReturn(payment);

        when(this.modelMapper.map(payment, PaymentReservationDTO.class))
                .thenReturn(paymentReservationDTO);
    }



    @Test
    @WithMockUser(username = "Guest", roles = {"GUEST"})
    void cardDetailsPageIsShown() throws Exception {

        this.mockMvc.perform(get("/user/reservations/pay/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("paymentReservationDTO"))
                .andExpect(view().name("card-details"));
    }



    @Test
    @WithMockUser(username = "Guest", roles = {"GUEST"})
    void cardDetailsArePostAndRecieved() throws Exception {

        this.mockMvc.perform(post("/user/reservations/pay/{id}", 1L)
                        .param("cvv", "123")
                        .param("owner", "Guest")
                        .param("cardNumber", "1234567890")
                        .param("expirationMonth", "MARCH")
                        .param("expirationYear", "2024").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

}
