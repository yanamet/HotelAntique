package com.example.hotelantique.service;

import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository mockRoleRepository;
    private RoleService toTest;
    private Role testGuestRole = new Role();
    private final static long GUEST_ROLE_ID = 3l;
    private Role testEmployeeRole = new Role();
    private final static long EMPLOYEE_ROLE_ID = 2l;
    private Role testAdminRole = new Role();
    private final static long ADMIN_ROLE_ID = 1l;

    @BeforeEach
    void setUp() {
        toTest = new RoleService(mockRoleRepository);

        testGuestRole.setName(RoleEnum.GUEST);
        testGuestRole.setId(GUEST_ROLE_ID);

        testEmployeeRole.setName(RoleEnum.EMPLOYEE);
        testEmployeeRole.setId(EMPLOYEE_ROLE_ID);

        testAdminRole.setName(RoleEnum.ADMIN);
        testAdminRole.setId(ADMIN_ROLE_ID);
    }

    @Test
    void getRoleByNameReturnsTheCorrectRole() {

        lenient().when(mockRoleRepository.findByName(RoleEnum.GUEST))
                .thenReturn(testGuestRole);

        lenient().when(mockRoleRepository.findByName(RoleEnum.EMPLOYEE))
                .thenReturn(testEmployeeRole);

        lenient().when(mockRoleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(testAdminRole);

        Role guestRole = this.toTest.getRoleByName(RoleEnum.GUEST);
        Assertions.assertEquals(testGuestRole.getName(), guestRole.getName());

        Role employeeRole = this.toTest.getRoleByName(RoleEnum.EMPLOYEE);
        Assertions.assertEquals(testEmployeeRole.getName(), employeeRole.getName());

        Role adminRole = this.toTest.getRoleByName(RoleEnum.ADMIN);
        Assertions.assertEquals(testAdminRole.getName(), adminRole.getName());
    }

    @Test
    void getRoleByIdReturnsTheCorrectRole() {

        lenient().when(mockRoleRepository.findById(GUEST_ROLE_ID))
                .thenReturn(Optional.of(testGuestRole));

        lenient().when(mockRoleRepository.findById(EMPLOYEE_ROLE_ID))
                .thenReturn(Optional.of(testEmployeeRole));

        lenient().when(mockRoleRepository.findById(ADMIN_ROLE_ID))
                .thenReturn(Optional.of(testAdminRole));

        Role guestRole = this.toTest.getRoleById(GUEST_ROLE_ID);
        Assertions.assertEquals(testGuestRole.getName(), guestRole.getName());

        Role employeeRole = this.toTest.getRoleById(EMPLOYEE_ROLE_ID);
        Assertions.assertEquals(testEmployeeRole.getName(), employeeRole.getName());

        Role adminRole = this.toTest.getRoleById(ADMIN_ROLE_ID);
        Assertions.assertEquals(testAdminRole.getName(), adminRole.getName());


    }

    @Test
    void getRolesToAddReturnsTheCorrectRoles(){

        Role currentUserRoleTest = testGuestRole;

        List<Role> currentRolesTest = List.of(currentUserRoleTest);
        List<Role> allRoles = List.of(testAdminRole, testEmployeeRole, testGuestRole);

        when(this.mockRoleRepository.findAll())
                .thenReturn(allRoles);

        List<Role> rolesToAdd = this.toTest.getRolesToAdd(currentRolesTest);

        Assertions.assertEquals(2, rolesToAdd.size());
        Assertions.assertEquals(testAdminRole, rolesToAdd.get(0));
        Assertions.assertEquals(testEmployeeRole, rolesToAdd.get(1));

    }

}
