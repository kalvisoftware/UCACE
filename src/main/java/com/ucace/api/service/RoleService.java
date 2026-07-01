package com.ucace.api.service;

import java.util.List;

import com.ucace.api.entity.Role;

public interface RoleService {

    Role saveRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role updateRole(Long id, Role role);

    String deleteRole(Long id);
}
