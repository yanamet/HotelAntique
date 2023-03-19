package com.example.hotelantique.repository;

import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleEnum name);

}
