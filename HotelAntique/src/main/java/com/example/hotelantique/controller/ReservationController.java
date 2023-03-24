package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;

    public ReservationController(ReservationService reservationService,
                                 RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @ModelAttribute("reservationDTO")
    public ReservationDTO initReservationDTO(){
        return new ReservationDTO();
    }

    @GetMapping("/reservations/add")
    public String addReserve(){
//        Room room = this.roomService.getRoomById(roomId);
//        model.addAttribute("room", room);

//        @PathVariable long roomId,
//        Model model)
        return "reservation-add";
    }

    @PostMapping("/reservations/add")
    public String addReserve(@Valid ReservationDTO reservationDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetails userDetails){



        if(bindingResult.hasErrors() || !this.reservationService.saveReservation(reservationDTO, userDetails.getUsername()) ){


            redirectAttributes.addFlashAttribute("reservationDTO", reservationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerDTO", bindingResult);

            return "redirect:/reservations/add";
        }

        return "successful-reservation";
    }

    @GetMapping("/reservations/successful")
    public String successfulReservation(){
        return "successful-reservation";
    }

    @GetMapping("/payment/card")
    public String cardPayment(){
        return "card-details";
    }

}
