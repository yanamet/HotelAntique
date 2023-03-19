package com.example.hotelantique.model.entity;

import com.example.hotelantique.model.enums.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleEnum name;

    public Role() {
    }

    public Role(RoleEnum roleEnum) {
        this.name = roleEnum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum role) {
        this.name = role;
    }
}
