package com.matrimony.controller;

import com.matrimony.entity.Role;
import com.matrimony.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {


    @Autowired
    private RoleService roleService;

    // create new role
    @PostMapping("/createRole")

    public Role createRole(@RequestBody Role role) {
        return roleService.saveRole(role);

    }

    // get all roles
    @GetMapping("/getAllRoles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();

    }

    // get role
    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleService.getRoleById(id);

    }

    // delete role
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

    }

}
