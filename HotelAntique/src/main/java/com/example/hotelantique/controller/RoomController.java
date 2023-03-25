package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.roomDTO.AvailableRoomSearchDTO;
import com.example.hotelantique.model.dtos.roomDTO.RoomViewDTO;
import com.example.hotelantique.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
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
                                 Model model){

        System.out.println("I am in PostMapping available rooms");

        if (bindingResult.hasErrors()) {
            model.addAttribute("availableRoomDTO", availableRoomDTO);
            model.addAttribute(
                    "org.springframework.validation.BindingResult.availableRoomDTO",
                    bindingResult);
            return "/rooms/available/search";
        }

        System.out.println(availableRoomDTO);


        return "available-rooms-search";
    }

}
