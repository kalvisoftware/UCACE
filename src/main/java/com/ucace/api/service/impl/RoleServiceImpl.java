package com.ucace.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucace.api.dto.RoleRequestDTO;
import com.ucace.api.dto.RoleResponseDTO;
import com.ucace.api.entity.Role;
import com.ucace.api.repository.RoleRepository;
import com.ucace.api.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleResponseDTO saveRole(RoleRequestDTO role) {
        Role roleEntity = convertToEntity(role);
        Role savedRole = roleRepository.save(roleEntity);
        return convertToDTO(savedRole);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        List<Role> getAllUserList = roleRepository.findAll();
        return getAllUserList.stream().map(role -> {
            return convertToDTO(role);
        }).collect(Collectors.toList());
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role Not Found"));
        return convertToDTO(role);
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO role) {

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role Not Found"));

        existingRole.setRoleName(role.getRoleName());
        existingRole.setDescription(role.getDescription());
        existingRole.setStatus(role.getStatus());
        existingRole.setUpdatedDate(LocalDateTime.now());

        Role updatedRole = roleRepository.save(existingRole);

        return convertToDTO(updatedRole);
    }

    @Override
    public String deleteRole(Long id) {
        roleRepository.deleteById(id);
        return "Role deleted successfully";
    }

    public Role convertToEntity(RoleRequestDTO role) {
        Role roleEntity = new Role();
        roleEntity.setRoleName(role.getRoleName());
        roleEntity.setDescription(role.getDescription());
        roleEntity.setStatus(role.getStatus());
        roleEntity.setCreatedDate(LocalDateTime.now());
        roleEntity.setUpdatedDate(LocalDateTime.now());
        return roleEntity;
    }

    public RoleResponseDTO convertToDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());
        dto.setStatus(role.getStatus());
        dto.setCreatedDate(role.getCreatedDate());
        dto.setUpdatedDate(role.getUpdatedDate());
        return dto;
    }

    public RoleResponseDTO updateToDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());
        dto.setStatus(role.getStatus());
        dto.setUpdatedDate(LocalDateTime.now());
        return dto;
    }

}
