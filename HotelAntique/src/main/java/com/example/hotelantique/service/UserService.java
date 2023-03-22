package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService,
                       ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
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
            user.setGender("FEMALE");
            user.setPassword(this.passwordEncoder.encode("12345"));

            UserEntity secUser = new UserEntity();
            secUser.setUsername("Moderator");
            secUser.setFullName("Moderator Moderatorov");
            secUser.setEmail("moderator@abv.bg");
            secUser.setRoles(List.of(guestRole, employeeRole));
            secUser.setPhoneNumber("+359ADMIN");
            secUser.setGender("MALE");
            secUser.setPassword(this.passwordEncoder.encode("12345"));

            UserEntity guest = new UserEntity();
            guest.setUsername("GUEST");
            guest.setFullName("GUEST GUEST");
            guest.setEmail("GUEST@abv.bg");
            guest.setRoles(List.of(guestRole));
            guest.setGender("FEMALE");
            guest.setPhoneNumber("+359ADMIN");
            guest.setPassword(this.passwordEncoder.encode("12345"));

            this.userRepository.save(user);
            this.userRepository.save(secUser);
            this.userRepository.save(guest);


        }

    }

    public   Optional<UserEntity>  getByUsername(String username) {
     return this.userRepository.findByUsername(username);
    }

    public   Optional<UserEntity> getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void register(UserEntity user) {
        this.userRepository.save(user);
    }

    public List<UserAdminPageDTO> getAllUsersOtherThan(long id) {
       List<UserEntity> users = this.userRepository.findByIdNot(id);

        return users
                .stream()
                .map(u -> this.modelMapper.map(u, UserAdminPageDTO.class))
                .collect(Collectors.toList());
    }
}
