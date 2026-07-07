package com.ucace.api.service;

import java.util.List;

import com.ucace.api.dto.RoleRequestDTO;
import com.ucace.api.dto.RoleResponseDTO;

public interface RoleService {

    RoleResponseDTO saveRole(RoleRequestDTO role);

    List<RoleResponseDTO> getAllRoles();

    RoleResponseDTO getRoleById(Long id);

    RoleResponseDTO updateRole(Long id, RoleRequestDTO role);

    String deleteRole(Long id);
}
