package com.example.hotelantique.service;

import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService toTest;
    private UserEntity admin;
    private UserEntity guest;


    @BeforeEach
    void setUp() {
        this.toTest = new UserService(userRepository, roleService, modelMapper, passwordEncoder);

        admin = new UserEntity();
        admin.setFullName("Admin Adminov");
        admin.setId(1L);
        admin.setUsername("Admin");
        admin.setPassword("topsecret");
        admin.setEmail("admin@adminov.bg");
        admin.setGender("FEMALE");
        admin.setPhoneNumber("00000000000");

        guest = new UserEntity();
        guest.setFullName("Guest Gestov");
        guest.setId(2L);
        guest.setUsername("Guest");
        guest.setPassword("topsecret");
        guest.setEmail("guest@gestov.bg");
        guest.setGender("MALE");
        guest.setPhoneNumber("00000000001");
    }

    @Test
    void notAdminUsersAreReturnedCorrectly() {

        when(this.userRepository.findByIdNot(1L))
                .thenReturn(List.of(guest));

        UserAdminPageDTO userAdminPageDTO = new UserAdminPageDTO();
        userAdminPageDTO.setId(guest.getId());
        userAdminPageDTO.setUsername(guest.getUsername());

        when(this.modelMapper.map(guest, UserAdminPageDTO.class))
                .thenReturn(userAdminPageDTO);

        List<UserAdminPageDTO> allUsersOtherThan = this.toTest.getAllUsersOtherThan(1L);

        Assertions.assertEquals(1, allUsersOtherThan.size());
        Assertions.assertEquals(guest.getId(), allUsersOtherThan.get(0).getId());
        Assertions.assertEquals(guest.getUsername(), allUsersOtherThan.get(0).getUsername());

    }

    @Test
    void deleteByIdMethodDeletesTheUser(){
        this.toTest.deleteById(2L);
        verify(this.userRepository).deleteById(2L);
    }

    @Test
    void getUserByIdReturnsTheRightUser(){

        when(this.userRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        UserEntity returnedUser = this.toTest.getUserById(1L).get();
        Assertions.assertEquals(admin.getFullName(), returnedUser.getFullName());
        Assertions.assertEquals(admin.getUsername(), returnedUser.getUsername());
        Assertions.assertEquals(admin.getEmail(), returnedUser.getEmail());
        Assertions.assertEquals(admin.getPhoneNumber(), returnedUser.getPhoneNumber());
    }



    @Test
    void addRoleWorksProperly(){
        Role testEmployeeRole = new Role(RoleEnum.EMPLOYEE);
        testEmployeeRole.setId(1L);

        when(this.toTest.getUserById(2L))
                .thenReturn(Optional.of(guest));

        when(this.roleService.getRoleById(1L))
                .thenReturn(testEmployeeRole);

        this.toTest.addRole(1L, 2L);

        Assertions.assertEquals(1, guest.getRoles().size());
        Assertions.assertEquals(RoleEnum.EMPLOYEE, guest.getRoles().get(0).getName());

    }


    @Test
    void removeRoleWorksProperly(){
        Role testEmployeeRole = new Role(RoleEnum.EMPLOYEE);
        testEmployeeRole.setId(1L);

        when(this.toTest.getUserById(2L))
                .thenReturn(Optional.of(guest));

        when(this.roleService.getRoleById(1L))
                .thenReturn(testEmployeeRole);

        this.toTest.addRole(1L, 2L);

        this.toTest.removeRole(1L, 2L);

        Assertions.assertEquals(0, guest.getRoles().size());

    }

    public  Optional<UserAdminPageDTO>  getUserAdminDTOById(long id) {
        return this.userRepository.findById(id)
                .map(u -> this.modelMapper.map(u, UserAdminPageDTO.class));
    }

    @Test
    void getUserAdminDTOByIdReturnsTheCorrectUser(){

        when(this.userRepository.findById(2L))
                .thenReturn(Optional.of(guest));

        UserAdminPageDTO userAdminPageDTO = new UserAdminPageDTO();
        userAdminPageDTO.setId(guest.getId());
        userAdminPageDTO.setUsername(guest.getUsername());

        when(this.modelMapper.map(guest, UserAdminPageDTO.class))
                .thenReturn(userAdminPageDTO);

        UserAdminPageDTO returnedUser = toTest.getUserAdminDTOById(2L).get();
        Assertions.assertEquals(userAdminPageDTO.getId(), returnedUser.getId());
        Assertions.assertEquals(userAdminPageDTO.getUsername(), returnedUser.getUsername());


    }


}
