package com.example.hotelantique.model.dtos.userDTO;

import com.example.hotelantique.model.entity.Role;

import java.util.List;

public class UserAdminPageDTO {
    private long id;
    private String username;
    private List<Role> roles;

    public UserAdminPageDTO() {
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserAdminPageDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
