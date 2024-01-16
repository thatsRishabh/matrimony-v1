package com.matrimony.service;

import com.matrimony.entity.Role;

import java.util.List;

public interface RoleService {
    public Role saveRole (Role role);

    public List<Role> getAllRoles();

    public Role getRoleById(Long id);

    public void deleteRole(Long id);
}
