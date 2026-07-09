package com.ucace.api.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ucace.api.dto.RoleRequestDTO;
import com.ucace.api.dto.RoleResponseDTO;
import com.ucace.api.entity.Role;
import com.ucace.api.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role Management", description = "APIs for managing roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @Operation(summary = "Save Role", description = "Create a new role")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<RoleResponseDTO> saveRole(@Valid @RequestBody RoleRequestDTO role) {
        RoleResponseDTO savedRole = roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @GetMapping
    @Operation(summary = "Get All Roles", description = "Retrieve a list of all roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> getAllRoles = roleService.getAllRoles();
        return ResponseEntity.ok(getAllRoles);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Role by ID", description = "Retrieve a role by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        RoleResponseDTO getRoleById = roleService.getRoleById(id);
        return ResponseEntity.ok(getRoleById);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update Role", description = "Update an existing role by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<RoleResponseDTO> updateRole(@Valid @RequestBody RoleRequestDTO role, @PathVariable Long id) {
        RoleResponseDTO updateRole = roleService.updateRole(id, role);
        return ResponseEntity.ok(updateRole);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Role", description = "Delete a role by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        String deleteRole = roleService.deleteRole(id);
        return ResponseEntity.ok(deleteRole);
    }
}
