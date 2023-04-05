package com.example.hotelantique.controller;

import com.example.hotelantique.model.dtos.reservationDTO.ReservationViewDTO;
import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.model.entity.Reservation;
import com.example.hotelantique.model.entity.Room;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.user.HotelAntiqueApplicationUserDetails;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminPageControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @MockBean
    private HotelAntiqueApplicationUserDetails userDetails;
    @MockBean
    private ReservationService reservationService;
    UserEntity admin;
    UserEntity guest;
    UserAdminPageDTO guestAdminPageDTO;
    ReservationViewDTO reservation;

    @BeforeEach
    void setUp(){

        admin = new UserEntity();
        admin.setUsername("ADMIN");
        admin.setId(1L);
        admin.setPassword("12345");
        admin.setFullName("Admin Adminov");
        admin.setGender("FEMALE");
        admin.setEmail("admin@adminov.bg");
        admin.setPhoneNumber("0000000000");

        this.userService.register(admin);

        guest = new UserEntity();
        guest.setFullName("Guest Gestov");
        guest.setId(2L);
        guest.setUsername("Guest");
        guest.setPassword("topsecret");
        guest.setEmail("guest@gestov.bg");
        guest.setGender("MALE");
        guest.setPhoneNumber("00000000001");

        this.userService.register(guest);

        guestAdminPageDTO = new UserAdminPageDTO();
        guestAdminPageDTO.setUsername(guest.getUsername());
        guestAdminPageDTO.setId(guest.getId());

        reservation = new ReservationViewDTO();
        reservation.setActive(true);
        reservation.setCheckInAndCheckOut("2023-04-06 - 2023-04-08");
        reservation.setGuestUsername(guest.getUsername());
        reservation.setGuestFullName(guest.getFullName());
        reservation.setId(2L);

        when(userService.getByUsername("ADMIN"))
                .thenReturn(Optional.of(admin));

        userService.getAllUsersOtherThan(admin.getId());
        when(userService.getAllUsersOtherThan(1L))
                .thenReturn(List.of(guestAdminPageDTO));

    }


    @Test
    @WithMockUser(username = "Guest", roles = {"GUEST"})
    void testAdminPageAccessDeniedForUserWithoutAdminRole() throws Exception {
        mockMvc.perform(get("/pages/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = {"ADMIN"})
    void testAdminPageReturnsTheRightListOfUsers() throws Exception {
        mockMvc.perform(get("/pages/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(2)))
                .andExpect(jsonPath("$.[0].username", is(guest.getUsername())));
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = {"ADMIN"})
    void testAdminPageReturnsTheRightListOfReservations() throws Exception {

        when(this.reservationService.getAllReservations())
                .thenReturn(List.of(reservation));

        mockMvc.perform(get("/pages/admin/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(2)))
                .andExpect(jsonPath("$.[0].guestUsername", is(guest.getUsername())))
                .andExpect(jsonPath("$.[0].guestFullName", is(guest.getFullName())))
                .andExpect(jsonPath("$.[0].checkInAndCheckOut", is(reservation.getCheckInAndCheckOut())));
    }


}
