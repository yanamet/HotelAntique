package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.roomDTO.AvailableRoomFoundDTO;
import com.example.hotelantique.model.dtos.roomDTO.RoomViewDTO;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @MockBean
    private ReservationService reservationService;

    private RoomViewDTO roomViewDTO;

    private AvailableRoomFoundDTO availableRoomFoundDTO;

    @BeforeEach
    void setUp(){
        roomViewDTO = new RoomViewDTO();
        roomViewDTO.setId(1L);
        roomViewDTO.setType("STANDARD");
        roomViewDTO.setDescription("Descr");
        roomViewDTO.setPrice(BigDecimal.valueOf(90));
        roomViewDTO.setName("Double Standard");

        availableRoomFoundDTO = new AvailableRoomFoundDTO();
        availableRoomFoundDTO.setCheckIn(LocalDate.of(2023, 5,6));
        availableRoomFoundDTO.setCheckOut(LocalDate.of(2023, 5, 8));
        availableRoomFoundDTO.setName("DOUBLE STANDARD");
        availableRoomFoundDTO.setRoomNumber(101);
        availableRoomFoundDTO.setId(1L);
    }


    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void getAllRoomsReturnsTheCorrectListOfRoomViewDTOs() throws Exception {
        this.mockMvc.perform(get("/rooms/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allRooms"))
                .andExpect(view().name("all-rooms"));
    }

    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void getRoomDetails() throws Exception {

        when(this.roomService.getRoomDetailsViewById(1L)).thenReturn(roomViewDTO);

        this.mockMvc.perform(get("/rooms/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("room"))
                .andExpect(view().name("details"));
    }

    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void availableRoomPageIsShown() throws Exception {
        this.mockMvc.perform(get("/rooms/available/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("available-rooms-search"));
    }

    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void findingAvailableRoomsWorks() throws Exception {

        when(this.reservationService.getAvailableRoomsInPeriod(LocalDate.of(2023, 5,6),
                LocalDate.of(2023, 5,8), "STANDARD"))
                .thenReturn(List.of(availableRoomFoundDTO));

        this.mockMvc.perform(get("/rooms/available/search")
                        .param("checkIn", "2023-05-06")
                        .param("checkOut", "2023-05-08")
                        .param("roomType", "STANDARD"))
                .andExpect(status().isOk())
                .andExpect(view().name("available-rooms-search"));
    }

    //      @PostMapping("/rooms/available/search")
    //    public String availableRooms(@Valid AvailableRoomSearchDTO availableRoomDTO,
    //                                 BindingResult bindingResult,
    //                                 RedirectAttributes redirectAttributes,
    //                                 Model model){
    //
    //
    //        if (bindingResult.hasErrors()) {
    //
    //            redirectAttributes.addFlashAttribute("availableRoomDTO", availableRoomDTO);
    //            redirectAttributes.addFlashAttribute(
    //                    "org.springframework.validation.BindingResult.availableRoomDTO",
    //                    bindingResult);
    //            return "redirect:/rooms/available/search";
    //        }
    //
    //        LocalDate checkIn = availableRoomDTO.getCheckIn();
    //        LocalDate checkOut = availableRoomDTO.getCheckOut();
    //        String roomType = availableRoomDTO.getRoomType();
    //
    //        List<AvailableRoomFoundDTO> availableRooms = this.reservationService
    //                .getAvailableRoomsInPeriod(checkIn, checkOut, roomType);
    //
    //        model.addAttribute("availableRooms", availableRooms);
    //
    //        return "available-rooms-search";
    //    }



}
