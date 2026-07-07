package com.ucace.api.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ucace.api.dto.RoleRequestDTO;
import com.ucace.api.dto.RoleResponseDTO;
import com.ucace.api.entity.Role;
import com.ucace.api.service.RoleService;
import java.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/roles")

public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> saveRole(@Valid @RequestBody RoleRequestDTO role) {
        RoleResponseDTO savedRole = roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> getAllRoles = roleService.getAllRoles();
        return ResponseEntity.ok(getAllRoles);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        RoleResponseDTO getRoleById = roleService.getRoleById(id);
        return ResponseEntity.ok(getRoleById);
    }

    @PutMapping("{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@Valid @RequestBody RoleRequestDTO role, @PathVariable Long id) {
        RoleResponseDTO updateRole = roleService.updateRole(id, role);
        return ResponseEntity.ok(updateRole);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        String deleteRole = roleService.deleteRole(id);
        return ResponseEntity.ok(deleteRole);
    }
}
