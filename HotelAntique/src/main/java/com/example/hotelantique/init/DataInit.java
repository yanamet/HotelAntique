package com.example.hotelantique.init;

import com.example.hotelantique.service.RoleService;
import com.example.hotelantique.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataInit(RoleService roleService, UserService userService,
                    PasswordEncoder passwordEncoder,
                    @Value("${app.default.password}") String defaultPassword) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.roleService.seedRoleData();
        this.userService.initFirstUser();
    }

}
