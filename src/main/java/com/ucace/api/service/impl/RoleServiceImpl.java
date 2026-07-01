package com.ucace.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucace.api.entity.Role;
import com.ucace.api.repository.RoleRepository;
import com.ucace.api.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    // @Autowired
    // private RoleRepository roleRepository;

    private final RoleRepository roleRepository;

    private RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            existingRole.setRoleName(role.getRoleName());
            existingRole.setDescription(role.getDescription());
            existingRole.setStatus(role.getStatus());
            existingRole.setCreatedDate(role.getCreatedDate());
            existingRole.setUpdatedDate(role.getUpdatedDate());
            return roleRepository.save(existingRole);
        }
        return null;
    }

    @Override
    public String deleteRole(Long id) {
        roleRepository.deleteById(id);
        return "Role deleted successfully";
    }
}
