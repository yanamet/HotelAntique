package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationDTO;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.enums.RoomType;
import com.example.hotelantique.model.user.HotelAntiqueApplicationUserDetails;
import com.example.hotelantique.repository.RoomRepository;
import com.example.hotelantique.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;


    @MockBean
    HotelAntiqueApplicationUserDetails userDetails;
    Room room;
    RoomType roomType;

    ReservationDTO reservationDTO;




    @BeforeEach
    void setUp() {


        roomType = RoomType.STANDARD;
        room = new Room();
        room.setRoomType(roomType);
        room.setRoomNumber(101);
        room.setId(1L);
        room.setAvailable(true);
        room.setName("DOUBLE STANDARD");
        room.setPrice(BigDecimal.valueOf(90));

        reservationDTO = new ReservationDTO();
        reservationDTO.setRoomId(room.getId());
        reservationDTO.setRoomType(room.getRoomType().name());
        reservationDTO.setRoomNumber(room.getRoomNumber());

        this.roomRepository.save(room);




        GrantedAuthority authority = new SimpleGrantedAuthority
                ("ROLE_GUEST");
        userDetails = new HotelAntiqueApplicationUserDetails(
                1L, "GUEST", "Guest Gestov", "email@abv.bg", "12345",
                "0000000000", "MALE", List.of(authority)
        );

        when(userDetails.getUsername())
                .thenReturn("GUEST");

        when(this.roomRepository.findById(1L))
                .thenReturn(Optional.of(room));

        when(this.roomService.getRoomById(1L))
                .thenReturn(room);

        when(this.reservationService.createReservationDTO(room, "2023-04-06", "2023-04-08"))
                .thenReturn(reservationDTO);


    }


    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void testReservationPageIsShown() throws Exception {

        mockMvc.perform(get("/reservations/add/{id}/{checkIn}/{checkOut}",
                        1L, "2023-04-06", "2023-04-08"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservationDTO"))
                .andExpect(view().name("reservation-add"));

    }

    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void testReservationPostWorks() throws Exception {


        mockMvc.perform(post("/reservations/add/{id}/{checkIn}/{checkOut}",
                        1L, "2023-04-06", "2023-04-08")
                        .with(csrf())
                        .param("fullName", "Full Name")
                        .param("roomType", "STANDARD")
                        .param("roomNumber", String.valueOf(101))
                        .param("checkIn", "2023-04-06")
                        .param("checkOut", "2023-04-08")
                        .param("email", "email@abv.bg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations/successful"));


    }

    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void testReservationPostWithErrorsRedirectsToTheSamePage() throws Exception {


        mockMvc.perform(post("/reservations/add/{id}/{checkIn}/{checkOut}",
                        1L, "2023-04-06", "2023-04-08")
                        .with(csrf())
                        .param("fullName", "")
                        .param("roomType", "STANDARD")
                        .param("roomNumber", String.valueOf(101))
                        .param("checkIn", "2023-04-06")
                        .param("checkOut", "2023-04-08")
                        .param("email", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations/add/1/2023-04-06/2023-04-08"));

    }


    @Test
    @WithMockUser(username = "GUEST", roles = {"GUEST"})
    void successFullReservationIsShown() throws Exception {
        mockMvc.perform(get("/reservations/successful"))
                .andExpect(status().isOk())
                .andExpect(view().name("successful-reservation"));
    }

}
