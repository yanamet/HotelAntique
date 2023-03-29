package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.userDTO.UserRegisterDTO;
import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private static String USERNAME = "velin";
    private static String RAW_PASSWORD = "velin123";
    private static String FULL_NAME = "Velin Brishimov";
    private static String GENDER = "MALE";
    private static String EMAIL = "brishimov@mail.com";
    private static String PHONE_NUMBER = "0000000000";

    private static long VALID_ID = 1L;
    private static long NOT_VALID_ID = 156L;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private EmailService emailService;
    @Mock
    private SecurityContextRepository securityContextRepository;
    @Mock
    private RoleService roleService;

    private AuthService toTest;

    @BeforeEach
    void setUp(){
        this.toTest = new AuthService(userService, modelMapper, passwordEncoder, userDetailsService,
                emailService, securityContextRepository, roleService);
    }

    @Test
    void userRegistration(){

        Role testGuestRole = new Role(RoleEnum.GUEST);

        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername(USERNAME);
        registerDTO.setEmail(EMAIL);
        registerDTO.setPassword(RAW_PASSWORD);
    }

}
