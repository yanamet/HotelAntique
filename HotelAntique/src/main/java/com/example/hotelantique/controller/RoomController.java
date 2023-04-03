package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.roomDTO.AvailableRoomFoundDTO;
import com.example.hotelantique.model.dtos.roomDTO.AvailableRoomSearchDTO;
import com.example.hotelantique.model.dtos.roomDTO.RoomViewDTO;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RoomController {

    private final RoomService roomService;
    private final ReservationService reservationService;

    public RoomController(RoomService roomService, ReservationService reservationService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @ModelAttribute("availableRoomDTO")
    public AvailableRoomSearchDTO initAvailableRoom(){
        return new AvailableRoomSearchDTO();
    }

    @GetMapping("/rooms/all")
    public String allRooms(Model model){

        List<RoomViewDTO> allRooms = this.roomService.getAllRoomTypes();
        model.addAttribute("allRooms", allRooms);

        return "all-rooms";
    }

    @GetMapping("/rooms/details/{id}")
    public String roomDetails(@PathVariable long id,
                              Model model){
        RoomViewDTO roomDetailsViewById = this.roomService.getRoomDetailsViewById(id);
        model.addAttribute("room", roomDetailsViewById);
        return "details";
    }

    @GetMapping("/rooms/available/search")
    public String availableRooms(){
        return "available-rooms-search";
    }


    @PostMapping("/rooms/available/search")
    public String availableRooms(@Valid AvailableRoomSearchDTO availableRoomDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model){


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("availableRoomDTO", availableRoomDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.availableRoomDTO",
                    bindingResult);
            return "redirect:/rooms/available/search";
        }

        LocalDate checkIn = availableRoomDTO.getCheckIn();
        LocalDate checkOut = availableRoomDTO.getCheckOut();
        String roomType = availableRoomDTO.getRoomType();

        List<AvailableRoomFoundDTO> availableRooms = this.reservationService
                .getAvailableRoomsInPeriod(checkIn, checkOut, roomType);

        model.addAttribute("availableRooms", availableRooms);

        return "available-rooms-search";
    }

}
