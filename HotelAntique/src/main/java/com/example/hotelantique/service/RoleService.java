package com.example.hotelantique.service;

import com.example.hotelantique.model.enums.RoleEnum;
import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void seedRoleData() {
        if(this.roleRepository.count() == 0){

            List<Role> roles = Arrays.stream(RoleEnum.values())
                    .map(Role::new)
                    .collect(Collectors.toList());

            this.roleRepository.saveAll(roles);
        }
    }

    public Role getRoleByName(RoleEnum name){
       return this.roleRepository.findByName(name);
    }



    public List<Role> getRolesToAdd(List<Role> userRoles) {
        List<Role> all = this.roleRepository.findAll();

        return all.stream()
                .filter(r -> !userRoles.contains(r))
                .collect(Collectors.toList());
    }

    public Role getRoleById(long roleId) {
        return this.roleRepository.findById(roleId).get();
    }

    public List<Role> getListOfRole(RoleEnum guest) {
        return List.of(this.roleRepository.findByName(guest));
    }
}
