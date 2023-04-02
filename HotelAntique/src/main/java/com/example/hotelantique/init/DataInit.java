package com.example.hotelantique.init;

import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.service.ReservationService;
import com.example.hotelantique.service.RoleService;
import com.example.hotelantique.service.RoomService;
import com.example.hotelantique.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInit implements CommandLineRunner {


    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoomService roomService;

    private final ReservationService reservationService;

    public DataInit(RoleService roleService, UserService userService,
                    PasswordEncoder passwordEncoder, RoomService roomService,
                    ReservationService reservationService) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roomService = roomService;
        this.reservationService = reservationService;

    }

    @Override
    public void run(String... args) throws Exception {

        this.roleService.seedRoleData();
        this.userService.initFirstUser();
        this.roomService.initRoomsData();
//        this.reservationService.initReservationTries();


    }

}
