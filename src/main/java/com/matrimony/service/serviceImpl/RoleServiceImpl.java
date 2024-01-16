package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Role;
import com.matrimony.repository.RoleRepository;
import com.matrimony.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role) {
        try {
            return roleRepository.save(role);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public List<Role> getAllRoles() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public Role getRoleById(Long id) {
        try {
            return roleRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public void deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);

        }

    }
}
