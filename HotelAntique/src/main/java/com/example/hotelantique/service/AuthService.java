package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.userDTO.UserRegisterDTO;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoleEnum;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AuthService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;
    private final RoleService roleService;

    public AuthService(UserService userService, ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
                       EmailService emailService, SecurityContextRepository securityContextRepository,
                       RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
        this.roleService = roleService;
    }

    public boolean register(UserRegisterDTO registerDTO,
                            Consumer<Authentication> successfulLoginProcessor) {

        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            return false;
        }

        Optional<UserEntity> byUsername = this.userService.getByUsername(registerDTO.getUsername());
        if(byUsername.isPresent()){
            return false;
        }

        Optional<UserEntity> byEmail = this.userService.getByEmail(registerDTO.getEmail());
        if(byEmail.isPresent()){
            return false;
        }

        UserEntity user = this.modelMapper.map(registerDTO, UserEntity.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRoles (this.roleService.getListOfRole(RoleEnum.GUEST));

        this.userService.register(user);

        this.emailService.sendRegistrationEmail(user.getUsername(), user.getEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(registerDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);

        return true;
    }


}
