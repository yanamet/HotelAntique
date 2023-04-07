package com.example.hotelantique.controller;


import com.example.hotelantique.model.dtos.userDTO.UserAdminPageDTO;
import com.example.hotelantique.model.entity.Role;
import com.example.hotelantique.service.RoleService;
import com.example.hotelantique.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RolesController {

    private final UserService userService;
    private final RoleService roleService;

    public RolesController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/roles/change/{id}")
    public String rolesChange(@PathVariable("id") long id,
                              Model model){
        UserAdminPageDTO user = this.userService.getUserAdminDTOById(id).get();

        List<Role> currentRoles = user.getRoles();
        List<Role> missingRoles = this.roleService.getRolesToAdd(currentRoles);

        model.addAttribute("user", user);
        model.addAttribute("currentRoles", currentRoles);
        model.addAttribute("missingRoles", missingRoles);

        return "roles-change";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/roles/add/{id}/{id2}")
    public String addRole(@PathVariable("id") long roleId,
                          @PathVariable("id2") long userId){

        this.userService.addRole(roleId, userId);

        return "admin-page";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/roles/remove/{id}/{id2}")
    public String removeRole(@PathVariable("id") long roleId,
                          @PathVariable("id2") long userId){

        this.userService.removeRole(roleId, userId);


        return "admin-page";
    }

}
