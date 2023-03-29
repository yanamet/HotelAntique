package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.PaymentReservationDTO;
import com.example.hotelantique.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ModelAttribute("paymentReservationDTO")
    public PaymentReservationDTO initRegisterDTO(){
        return new PaymentReservationDTO();
    }

    @GetMapping("/payment/card")
    public String cardPayment(){
        return "card-details";
    }

    @PostMapping("/payment/card")
    public String cardPayment(@Valid PaymentReservationDTO paymentReservationDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            System.out.println("i am in not valid");
            redirectAttributes.addFlashAttribute("paymentReservationDTO", paymentReservationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.paymentReservationDTO", bindingResult);

            return "redirect:/payment/card";
        }

        this.paymentService.addPayment(paymentReservationDTO);


        return "home";
    }

}
