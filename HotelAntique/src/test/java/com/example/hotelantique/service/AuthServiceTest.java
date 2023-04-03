package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.userDTO.UserRegisterDTO;
import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.model.user.HotelAntiqueApplicationUserDetails;
import com.example.hotelantique.repository.RoleRepository;
import com.example.hotelantique.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.function.Consumer;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private static String USERNAME = "velin";
    private static String RAW_PASSWORD = "velin123";
    private static String ENCODED_PASSWORD = "$2a$10$Pvel5bpmtH4Gnye58gMs7eGw8iHTZBI8EzcNKKsItJEaJMl8tFcqG";
    private static String FULL_NAME = "Velin Brishimov";
    private static String GENDER = "MALE";
    private static String EMAIL = "brishimov@mail.com";
    private static String PHONE_NUMBER = "0000000000";

    private static long VALID_ID = 1L;
    private static long NOT_VALID_ID = 156L;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;
    @Mock
    private UserService mockedUserService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockedRoleRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserDetailsService mockUserDetailsService;
    @Mock
    private EmailService mockEmailService;
    @Mock
    private SecurityContextRepository mockSecurityContextRepository;
    @Mock
    private RoleService mockRoleService;


    private AuthService toTest;

    @BeforeEach
    void setUp() {
        this.toTest = new AuthService(mockedUserService, mockModelMapper, mockPasswordEncoder,
                mockUserDetailsService,
                mockEmailService, mockSecurityContextRepository, mockRoleService);

    }


    @Test
    void userRegistration() {

        Role testGuestRole = new Role(RoleEnum.GUEST);

        lenient().when(mockedRoleRepository.findByName(RoleEnum.GUEST)).thenReturn(testGuestRole);

        UserRegisterDTO testRegisterDTO = new UserRegisterDTO();
        testRegisterDTO.setUsername(USERNAME);
        testRegisterDTO.setFullName(FULL_NAME);
        testRegisterDTO.setEmail(EMAIL);
        testRegisterDTO.setPassword(RAW_PASSWORD);
        testRegisterDTO.setConfirmPassword(RAW_PASSWORD);
        testRegisterDTO.setPhoneNumber(PHONE_NUMBER);
        testRegisterDTO.setGender(GENDER);

        UserEntity testUser = new UserEntity();
        testUser.setUsername(USERNAME);
        testUser.setFullName(FULL_NAME);
        testUser.setEmail(EMAIL);
        testUser.setPassword(RAW_PASSWORD);
        testUser.setPhoneNumber(PHONE_NUMBER);
        testUser.setGender(GENDER);
        testUser.setRoles(List.of(testGuestRole));


        when(mockModelMapper.map(testRegisterDTO, UserEntity.class)).thenReturn(testUser);

        lenient().when(mockUserRepository.save(any(UserEntity.class))).thenReturn(testUser);

        HotelAntiqueApplicationUserDetails userDetails = new HotelAntiqueApplicationUserDetails(
                1L, USERNAME, FULL_NAME, EMAIL, RAW_PASSWORD,
                PHONE_NUMBER, GENDER, Collections.emptyList()
        );

        when(mockUserDetailsService.loadUserByUsername(testRegisterDTO
                .getUsername())).thenReturn(userDetails);


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        Consumer<Authentication> successfulLoginProcessor = mock(Consumer.class);
        toTest.register(testRegisterDTO, successfulLoginProcessor);


//        Mockito.verify(mockedUserService).register(any());
//        verify(mockUserRepository).save(any());

    }

}
