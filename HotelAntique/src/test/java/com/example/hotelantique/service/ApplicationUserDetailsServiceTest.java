package com.example.hotelantique.service;

import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.entity.UserEntity;
import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.repository.UserRepository;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTest {

    private static String NON_EXISTING_EMAIL = "not_exist@abv.bg";
    private static String NON_EXISTING_USERNAME = "doNotExist";
    private static String EXISTING_USERNAME = "Admin";

    private ApplicationUserDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        this.toTest = new ApplicationUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserFound(){

        Role testAdminRole = new Role(RoleEnum.ADMIN);
        Role testGuestRole = new Role(RoleEnum.GUEST);

        UserEntity user = new UserEntity();
        user.setUsername(EXISTING_USERNAME);
        user.setPassword("12345");
        user.setRoles(List.of(testAdminRole, testGuestRole));

        when(mockUserRepository.findByUsername(EXISTING_USERNAME))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = toTest.loadUserByUsername(EXISTING_USERNAME);

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(EXISTING_USERNAME, userDetails.getUsername());
        Assertions.assertEquals("12345", userDetails.getPassword());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        assertRole(userDetails.getAuthorities(), "ROLE_ADMIN");
        assertRole(userDetails.getAuthorities(), "ROLE_GUEST");
    }

    private void assertRole(Collection<? extends GrantedAuthority> authorities,
                            String role){
        authorities
                .stream()
                .filter(r -> role.equals(r.getAuthority()))
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
    }

    @Test
    void testUserNotFound(){
        assertThrows(UsernameNotFoundException.class,
                () -> {
            toTest.loadUserByUsername(NON_EXISTING_USERNAME);
                });
    }


}
