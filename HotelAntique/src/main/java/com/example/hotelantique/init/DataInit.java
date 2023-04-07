package com.example.hotelantique.init;

import com.example.hotelantique.service.RoleService;
import com.example.hotelantique.service.RoomService;
import com.example.hotelantique.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {


    private final RoleService roleService;
    private final UserService userService;
    private final RoomService roomService;


    public DataInit(RoleService roleService,
                    UserService userService, RoomService roomService) {
        this.roleService = roleService;
        this.userService = userService;
        this.roomService = roomService;

    }

    @Override
    public void run(String... args) throws Exception {

        this.roleService.seedRoleData();
        this.userService.initFirstUser();
        this.roomService.initRoomsData();

    }

}
