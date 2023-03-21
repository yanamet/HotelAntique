package com.example.hotelantique.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    @Size(min = 3, max = 20)
    @NotEmpty
    private String username;

    @Size(min = 3, max = 20)
    @NotEmpty
    private String fullName;

    @Email
    private String email;

    @NotEmpty
    private String gender;

    @Size(min = 5, max = 20)
    @NotEmpty
    private String password;

    @Size(min = 5, max = 20)
    @NotEmpty
    private String confirmPassword;

    @Size(min = 10)
    @NotEmpty
    private String phoneNumber;

    public UserRegisterDTO() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
