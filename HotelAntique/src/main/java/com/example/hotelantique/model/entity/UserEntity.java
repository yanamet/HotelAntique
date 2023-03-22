package com.example.hotelantique.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String gender;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany
    private Set<Reservation> upcomingReservations;

    @OneToMany
    private Set<Reservation> previousReservations;

    public UserEntity() {
        this.roles = new ArrayList<>();
        this.previousReservations = new HashSet<>();
        this.upcomingReservations = new HashSet<>();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Reservation> getUpcomingReservations() {
        return upcomingReservations;
    }

    public void setUpcomingReservations(Set<Reservation> upcomingReservations) {
        this.upcomingReservations = upcomingReservations;
    }

    public Set<Reservation> getPreviousReservations() {
        return previousReservations;
    }

    public void setPreviousReservations(Set<Reservation> previousReservations) {
        this.previousReservations = previousReservations;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + (password != null ? "PROVIDED" : "no info") + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                ", upcomingReservations=" + upcomingReservations +
                ", previousReservations=" + previousReservations +
                '}';
    }
}
