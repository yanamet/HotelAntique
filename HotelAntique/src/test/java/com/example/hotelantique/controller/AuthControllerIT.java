package com.example.hotelantique.controller;

import com.example.hotelantique.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService mockedEmailService;

    @Test
    void testRegisterPageIsShown() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testLoginPageIsShown() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "Anna")
                        .param("fullName", "Anna Petrova")
                        .param("email", "anna@mail.com")
                        .param("gender", "FEMALE")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("phoneNumber", "0870000000")
                        .with(csrf()))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(mockedEmailService)
                .sendRegistrationEmail("Anna", "anna@mail.com");
        //String username, String userEmail

    }

    @Test
    void testRegisterAlreadyExistingUser() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "Test")
                        .param("fullName", "Test Testingov")
                        .param("email", "some@mail.com")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .param("phoneNumber", "0870000000")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }

    @Test
    void testLoginRightCredentials() throws Exception {
        mockMvc.perform(post("/login")
                  .param("username", "Test")
                  .param("password", "topsecret")
                  .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginFailureRedirect() throws Exception {
        mockMvc.perform(post("/login-error")
                        .param("username", "test")
                        .param("password", "wrongPass")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));



    }


}
