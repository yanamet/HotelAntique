package com.example.hotelantique.service;

import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    public void initFirstUser() {

        if(userRepository.count() == 0){
            Role adminRole = this.roleService.getRoleByName(RoleEnum.ADMIN);
            Role guestRole = this.roleService.getRoleByName(RoleEnum.GUEST);
            Role employeeRole = this.roleService.getRoleByName(RoleEnum.EMPLOYEE);


            UserEntity user = new UserEntity();
            user.setUsername("Admin");
            user.setFullName("Admin Adminov");
            user.setEmail("admin@abv.bg");
            user.setRoles(List.of(adminRole, guestRole, employeeRole));
            user.setPhoneNumber("+359ADMIN");
            user.setPassword(this.passwordEncoder.encode("12345"));

            this.userRepository.save(user);


        }



    }
}
