package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.UserRegisterDTO;
import com.example.hotelantique.model.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(UserRegisterDTO registerDTO) {


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
        this.userService.register(user);
        return true;
    }
}
