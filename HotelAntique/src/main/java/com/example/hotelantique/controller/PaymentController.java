package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.PaymentReservationDTO;
import com.example.hotelantique.model.entity.Payment;
import com.example.hotelantique.service.PaymentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    public PaymentController(PaymentService paymentService, ModelMapper modelMapper) {
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("paymentReservationDTO")
    public PaymentReservationDTO initRegisterDTO() {
        return new PaymentReservationDTO();
    }

    @GetMapping("/user/reservations/pay/{id}")
    public String cardPayment(@PathVariable("id") long reservationId,
                              Model model) {


        PaymentReservationDTO paymentReservationDTO = this.paymentService.createPayment(reservationId);

        model.addAttribute("paymentReservationDTO", paymentReservationDTO);


        return "card-details";
    }

    @PostMapping("/user/reservations/pay/{id}")
    public String cardPayment(@Valid PaymentReservationDTO paymentReservationDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @PathVariable long id) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("paymentReservationDTO", paymentReservationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.paymentReservationDTO", bindingResult);

            return "redirect:/user/reservations/pay/{id}";
        }

        this.paymentService.addPayment(paymentReservationDTO, id);


        return "home";
    }



}
