package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.userDTO.UserRegisterDTO;
import com.example.hotelantique.model.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final SecurityContextRepository securityContextRepository;


    public AuthService(UserService userService, ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
                       SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.securityContextRepository = securityContextRepository;
    }

    public boolean register(UserRegisterDTO registerDTO,
                            HttpServletRequest request,
                            HttpServletResponse response) {


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


        UserDetails userDetails = userDetailsService.loadUserByUsername(registerDTO.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

//        successfulLoginProcessor.accept(authentication);


        SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext context = strategy.createEmptyContext();
        context.setAuthentication(authentication);

        strategy.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        return true;
    }
}
